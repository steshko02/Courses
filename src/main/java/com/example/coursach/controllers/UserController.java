package com.example.coursach.controllers;

import com.example.coursach.converters.UserConverter;
import com.example.coursach.dto.ConfirmUserDto;
import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.repository.OtpRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createUser(@RequestBody RegistrationUserDto registrationUserDto) {
        userService.registrationUser(registrationUserDto);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void confirm(@RequestBody ConfirmUserDto registrationUserDto) {
        userService.confirmUser(registrationUserDto);
    }

}
