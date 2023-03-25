package com.example.coursach.repository;

import com.example.coursach.entity.notification.Notification;
import com.example.coursach.repository.custom.NotificationCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String>, NotificationCustomRepository {
}
