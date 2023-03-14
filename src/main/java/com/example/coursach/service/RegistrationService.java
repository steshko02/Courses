package com.example.coursach.service;

import com.example.coursach.config.properties.RegexProperties;
import com.example.coursach.converters.UserConverter;
import com.example.coursach.dto.security.RegisterConfirmDto;
import com.example.coursach.dto.security.RegisterRequestDto;
import com.example.coursach.entity.Code;
import com.example.coursach.entity.User;
import com.example.coursach.repository.OtpRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.security.utils.JwtTokenProvider;
import com.example.coursach.security.utils.UserDetailsFactory;
import com.example.coursach.service.model.RegistrationResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserDetailsFactory userDetailsFactory;

    private final JwtTokenProvider jwtTokenProvider;

    private final RegexProperties regexProperties;

    private final UserRepository userRepository;

    private final UserConverter userConverter;

//    private final EmailSenderService postman;

    private final OtpRepository codeRepository;

    private final Clock systemClock;

    public RegistrationService(
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsFactory userDetailsFactory,
            JwtTokenProvider jwtTokenProvider,
            RegexProperties regexProperties,
            UserRepository userRepository,
            UserConverter userConverter,
            OtpRepository codeRepository,
            Clock systemClock) {
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsFactory = userDetailsFactory;
        this.jwtTokenProvider = jwtTokenProvider;
        this.regexProperties = regexProperties;
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.codeRepository = codeRepository;
        this.systemClock = systemClock;
    }
    //CHECKSTYLE:ON

    @Transactional
    public void register(RegisterRequestDto registerDto) {

        User user = userRepository.findUserByEmail(registerDto.getEmail())
                .orElseGet(() -> userConverter.toEntity(registerDto, bCryptPasswordEncoder, UserRole.USER));

        if (user.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new UserAlreadyExistException(registerDto.getEmail());
        }

        if (!validPassword(registerDto.getPassword())) {
            throw new WeakPasswordException("*REGEX*");
        }

        Optional<Code> code = codeRepository.findCodeByRequestedEmail(user.getEmail());

        code.ifPresent(codeRepository::delete);

        user.setAccountStatus(AccountStatus.INVITED);
        userRepository.save(user);

        Code greetingsCode = codeRepository.save(Code.builder()
                .requested(user)
                .code(Generator.generateSixDigitalCode())
                .dateCreation(ZonedDateTime.now(systemClock).toLocalDateTime())
                .build());

        postman.send(
                Notification.buildGreetingsNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.GREETINGS,
                        user.getEmail(),
                        greetingsCode.getCode().toString()
                )
        );
    }

    @Transactional
    public RegistrationResult confirm(RegisterConfirmDto registerConfirmDto) {

        User user = userRepository.findUserByEmail(registerConfirmDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (user.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new UserAlreadyExistException(user.getEmail());
        }

        user = userConverter.updatePassword(registerConfirmDto, user, bCryptPasswordEncoder);
        user.setAccountStatus(AccountStatus.ACTIVE);
        userRepository.save(user);

        Optional.ofNullable(user.getCode()).ifPresent(codeRepository::delete);

        AuthorizedUser userDetails = userDetailsFactory.create(user);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(registerConfirmDto.getEmail(), registerConfirmDto.getPassword());
        authenticationManager.authenticate(authentication);

        return RegistrationResult.builder()
                .authenticationToken(jwtTokenProvider.createTokenWithBearerPrefix(userDetails))
                .build();
    }

    public boolean validPassword(String password) {
        if (!StringUtils.hasText(password)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regexProperties.getPassword());
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void resendPassword(ResendCodeRequestDto registerDto) {
        User user = userRepository.findUserByEmail(registerDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (user.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new UserAlreadyExistException(registerDto.getEmail());
        }

        codeRepository.delete(user.getCode());

        Code greetingsCode = codeRepository.save(Code.builder()
                .requested(user)
                .code(Generator.generateSixDigitalCode())
                .dateCreation(ZonedDateTime.now(systemClock).toLocalDateTime())
                .build());

        postman.send(
                Notification.buildGreetingsNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.GREETINGS,
                        user.getEmail(),
                        greetingsCode.getCode().toString()
                )
        );
    }
}
