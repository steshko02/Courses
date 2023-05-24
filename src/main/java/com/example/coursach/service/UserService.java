package com.example.coursach.service;

import com.example.coursach.dto.pagable.PageableRequestDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.dto.user.PasswordSettingRequestDto;
import com.example.coursach.dto.user.RecoveryRequestDto;
import com.example.coursach.dto.user.UserPagedDto;
import com.example.coursach.entity.Code;
import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.AccountStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.exception.user.InvalidCodeException;
import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.exception.user.WeakPasswordException;
import com.example.coursach.repository.CodeRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.filter.UserSpecification;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final CourseUserRepository courseUserRepository;
    private final LessonRepository lessonRepository;


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
            Clock systemClock, CourseUserRepository courseUserRepository, LessonRepository lessonRepository) {
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
        this.courseUserRepository = courseUserRepository;
        this.lessonRepository = lessonRepository;
    }
    //CHECKSTYLE:ON

    public UserPagedDto getAll(PageableRequestDto pageable, String query) {
        Pageable paging = PageRequest.of(pageable.getNumber() - 1, pageable.getSize());
        Page<User> users = userRepository.findAllByStatusAndEmailOrNickname(query, AccountStatus.ACTIVE, paging);
        return userConverter.userPagedResultToUserPagedDto(users, minioStorageService::getPictureUrl);
    }

    public List<BaseUserInformationDto> getAll(String username) {

        List<User> all = userRepository.findAll(UserSpecification.createBookingSpecification(username));
        return userConverter.listUserToListBaseUserInformationDto(all);
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
                        currentUser.getProfile().getUser().getFirstname() + " " + currentUser.getProfile().getUser().getLastname(),
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

    public List<BaseUserInformationDto> getMentorsOnCourse(Long id) {
        List<CourseUser> allByUserCourseIdCourseId = courseUserRepository.findById_CourseIdAndAndRole_Name(id, UserRole.LECTURER);

        List<User> users = userRepository.
                findAllByIds(allByUserCourseIdCourseId.stream()
                        .map(x -> x.getId().getUserId()).collect(Collectors.toList()));

        return userConverter.listUserToListBaseUserInformationDto(users);
    }

    public List<BaseUserInformationDto> getMentorsOnLesson(Long lessonId) {

        Optional<Lesson> byId = lessonRepository.findById(lessonId);

        return userConverter.listUserToListBaseUserInformationDto(byId.get().getMentors());
    }

    public BaseUserInformationDto getById(String uuid) {
        return userConverter.userToBaseUserInformationDto(userRepository.findById(uuid).get());
    }

    public Optional<CourseUser> getByRoleOnCourse(String uuid, Long courseId, UserRole userRole) {
        return courseUserRepository.findById_CourseIdAndId_UserIdAndRole_Name(courseId, uuid, userRole);
    }
    public Optional<CourseUser> getByRoleAndLessonOnCourse(String uuid, Long lessId, UserRole userRole) {
        Lesson lesson = lessonRepository.findById(lessId).orElseThrow(RuntimeException::new);
        return courseUserRepository.findById_CourseIdAndId_UserIdAndRole_Name(lesson.getCourse().getId(), uuid, userRole);
    }
}
