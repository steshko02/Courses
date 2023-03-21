package com.example.coursach.repository.custom;

import com.example.coursach.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface NotificationCustomRepository {

    Page<Notification> getAllNotificationsOfUser(String userUuid, Pageable pageable);

    void deleteAllOlderThan(LocalDateTime dateTime);

    Optional<Notification> findByUserUuidAndNotificationUuid(String userId, String notificationId);
}
