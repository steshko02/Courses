package com.example.coursach.exception.notifications;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class NotificationNotFoundException extends MessageWithErrorCodeException {
    public NotificationNotFoundException() {
        super(ErrorCode.NOTIFICATION_NOT_FOUND, HttpStatus.NOT_FOUND, "");
    }
}
