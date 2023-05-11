package com.example.coursach.controllers;

import com.example.coursach.config.properties.JwtProperties;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.dto.security.RegisterConfirmDto;
import com.example.coursach.dto.security.RegisterRequestDto;
import com.example.coursach.dto.user.PasswordSettingRequestDto;
import com.example.coursach.dto.user.RecoveryRequestDto;
import com.example.coursach.dto.user.security.JwtResponseDto;
import com.example.coursach.service.RegistrationService;
import com.example.coursach.service.UserService;
import com.example.coursach.service.model.RegistrationResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class AccountController {

    public static final String REGISTRATION_PATH = "/sign-up";

    private final UserService userService;

    private final JwtProperties jwtProperties;

    private final RegistrationService registrationService;

    public AccountController(UserService userService, JwtProperties jwtProperties,
                             RegistrationService registrationService) {
        this.userService = userService;
        this.jwtProperties = jwtProperties;
        this.registrationService = registrationService;
    }

    @PostMapping(REGISTRATION_PATH)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void register(@Valid @RequestBody RegisterRequestDto registerDto, HttpServletResponse response) {
        registrationService.register(registerDto);
    }

    @PostMapping(REGISTRATION_PATH + "/confirm")
    public ResponseEntity<JwtResponseDto> confirm(@Valid @RequestBody RegisterConfirmDto registerDto, HttpServletResponse response) {
        RegistrationResult registrationResult = registrationService.confirm(registerDto);
        response.addHeader(jwtProperties.getAccessTokenKey(), registrationResult.getAuthenticationToken());
        JwtResponseDto build = JwtResponseDto.builder().jwt(registrationResult.getAuthenticationToken()).build();
        return new ResponseEntity<>(build,HttpStatus.OK);
    }

    @PostMapping("/recovery-password")
    public StatusDto recoveryPassword(@RequestBody RecoveryRequestDto recoveryRequestDto) {
        return userService.passwordRecovery(recoveryRequestDto);
    }

    @PostMapping("/set-password")
    public StatusDto setPassword(@RequestBody PasswordSettingRequestDto passwordSettingRequestDto) {
        return userService.passwordSettingUp(passwordSettingRequestDto);
    }

}
