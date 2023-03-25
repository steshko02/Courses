package com.example.coursach.exception.user;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends MessageWithErrorCodeException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND, HttpStatus.FORBIDDEN);
    }

}
