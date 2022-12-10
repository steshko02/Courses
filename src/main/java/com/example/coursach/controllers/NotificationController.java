package com.example.coursach.controllers;

import com.example.coursach.dto.EventDto;
import com.example.coursach.dto.NotificationDto;
import com.example.coursach.service.EventService;
import com.example.coursach.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createNotification(@RequestBody NotificationDto notificationDto) {
        notificationService.createNotification(notificationDto);
    }
}
