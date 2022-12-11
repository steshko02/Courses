package com.example.coursach.service;

import com.example.coursach.converters.NotificationConverter;
import com.example.coursach.dto.NotificationDto;
import com.example.coursach.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationConverter notificationConvertor;

    public void createNotification(NotificationDto notificationDto) {
        notificationRepository.save(notificationConvertor.toEntity(notificationDto));
    }
}
