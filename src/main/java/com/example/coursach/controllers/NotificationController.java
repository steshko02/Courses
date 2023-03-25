package com.example.coursach.controllers;

import com.example.coursach.dto.notification.NotificationPagedDto;
import com.example.coursach.dto.notification.NotificationRequestParamsDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public NotificationPagedDto getAll(
            @Valid NotificationRequestParamsDto paramsDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return notificationService.getAll(paramsDto, authorizedUser.getUuid());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable String id,
            @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        notificationService.delete(id, authorizedUser.getUuid());
    }
}
