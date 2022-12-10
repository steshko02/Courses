package com.example.coursach.converters;

import com.example.coursach.dto.EventDto;
import com.example.coursach.dto.NotificationDto;
import com.example.coursach.entity.Event;
import com.example.coursach.entity.Notification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NotificationConverter {
    public Notification toEntity(NotificationDto notificationDto) {

        return Notification.builder()
                .title(notificationDto.getTitle())
                .description(notificationDto.getDescription())
                .date(notificationDto.getDateSend().toLocalDateTime())
                .type(notificationDto.getNotificationType())
                .build();
    }
}
