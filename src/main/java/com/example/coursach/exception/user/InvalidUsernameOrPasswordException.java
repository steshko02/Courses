package com.example.coursach.exception.user;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidUsernameOrPasswordException extends MessageWithErrorCodeException {

    public InvalidUsernameOrPasswordException() {
        super(ErrorCode.INVALID_EMAIL_OR_PASSWORD, HttpStatus.UNAUTHORIZED);
    }
}
