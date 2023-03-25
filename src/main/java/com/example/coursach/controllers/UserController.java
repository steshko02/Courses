package com.example.coursach.controllers;

import com.example.coursach.dto.pagable.PageableRequestDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.dto.user.UserPagedDto;
import com.example.coursach.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(UserController.USERS_PATH)
public class UserController {

    public static final String USERS_PATH = "/users";

    private final UserService userService;

    @GetMapping("{userUuid}")
    public BaseUserInformationDto getBaseUserInformation(@PathVariable String userUuid) {
        return userService.getBaseUserInformation(userUuid);
    }

    @GetMapping
    public UserPagedDto getAll(@Valid PageableRequestDto pageableRequestDto,
                               @RequestParam(defaultValue = StringUtils.EMPTY) String query) {
        return userService.getAll(pageableRequestDto, query);
    }
}
