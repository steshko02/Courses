package com.example.coursach.service;

import com.example.coursach.dto.pagable.PageableRequestDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.dto.user.PasswordSettingRequestDto;
import com.example.coursach.dto.user.RecoveryRequestDto;
import com.example.coursach.dto.user.UserPagedDto;
import com.example.coursach.entity.Code;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.AccountStatus;
import com.example.coursach.exception.user.InvalidCodeException;
import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.exception.user.WeakPasswordException;
import com.example.coursach.repository.CodeRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.UserConverter;
import com.example.coursach.service.model.LocalMessageCodes;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import com.example.coursach.service.utils.Generator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class UserService {

    private final CurrentUserRequestLocaleService currentUserRequestLocaleService;

    private final MinioStorageService minioStorageService;

    private final RegistrationService registrationService;

    private final LocalMessageService localMessageService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CodeRepository codeRepository;

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final EmailSenderService postman;

    private final Clock systemClock;

    //CHECKSTYLE:OFF
    public UserService(
            CurrentUserRequestLocaleService currentUserRequestLocaleService,
            MinioStorageService minioStorageService,
            RegistrationService registrationService,
            LocalMessageService localMessageService,
            BCryptPasswordEncoder passwordEncoder,
            CodeRepository codeRepository,
            UserRepository userRepository,
            UserConverter userConverter,
            EmailSenderService postman,
            Clock systemClock) {
        this.currentUserRequestLocaleService = currentUserRequestLocaleService;
        this.minioStorageService = minioStorageService;
        this.registrationService = registrationService;
        this.localMessageService = localMessageService;
        this.passwordEncoder = passwordEncoder;
        this.codeRepository = codeRepository;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.systemClock = systemClock;
        this.postman = postman;
    }
    //CHECKSTYLE:ON

    public UserPagedDto getAll(PageableRequestDto pageable, String query) {
        Pageable paging = PageRequest.of(pageable.getNumber() - 1, pageable.getSize());
        Page<User> users = userRepository.findAllByStatusAndEmailOrNickname(query, AccountStatus.ACTIVE, paging);
        return userConverter.userPagedResultToUserPagedDto(users, minioStorageService::getPictureUrl);
    }

    public BaseUserInformationDto getBaseUserInformation(String userUuid) {
        User currentUser = userRepository.findUserByIdWithFetchProfile(userUuid)
                .orElseThrow(UserNotFoundException::new);
        return userConverter.userToBaseUserInformationDto(currentUser, minioStorageService::getPictureUrl);
    }

    public StatusDto passwordRecovery(RecoveryRequestDto recoveryRequestDto) {
        User currentUser = userRepository.findUserByEmailExcludedInvitedWithFetchProfile(recoveryRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        Code recoveryCode = codeRepository.save(Code.builder()
                .requested(currentUser)
                .code(Generator.generateSixDigitalCode())
                .dateCreation(ZonedDateTime.now(systemClock).toLocalDateTime())
                .build());

        postman.send(
                Notification.buildRecoveryNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.RECOVERY,
                        currentUser.getEmail(),
                        currentUser.getProfile().getNickname(),
                        recoveryCode.getCode()
                )
        );

        return buildSuccessfulStatus(LocalMessageCodes.NEW_PASSWORD_SUCCESS_REQUESTED);
    }

    public StatusDto passwordSettingUp(PasswordSettingRequestDto passwordSettingRequestDto) {
        if (!registrationService.validPassword(passwordSettingRequestDto.getNewPassword())) {
            throw new WeakPasswordException("*REGEX*");
        }

        Code currentCode = codeRepository.findCodeByRequestedEmail(passwordSettingRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        LocalDateTime expirationDate = currentCode.getDateCreation().plusDays(1);

        if (expirationDate.isBefore(ZonedDateTime.now(systemClock).toLocalDateTime())) {
            codeRepository.delete(currentCode);
            throw new InvalidCodeException();
        }

        if (!currentCode.getCode().equals(passwordSettingRequestDto.getCode())) {
            throw new InvalidCodeException();
        }

        User currentUser = currentCode.getRequested();
        currentUser.setPassword(passwordEncoder.encode(passwordSettingRequestDto.getNewPassword()));

        userRepository.save(currentUser);
        codeRepository.delete(currentCode);
        return buildSuccessfulStatus(LocalMessageCodes.NEW_PASSWORD_SUCCESS);
    }

    private StatusDto buildSuccessfulStatus(LocalMessageCodes localMessageCode) {
        return StatusDto.builder()
                .message(localMessageService.getMessage(localMessageCode))
                .timestamp(LocalDateTime.now().toString())
                .build();
    }

}
