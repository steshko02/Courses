package com.example.coursach.exception.user;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends MessageWithErrorCodeException {

    public UserAlreadyExistException(String email) {
        super(ErrorCode.USER_ALREADY_EXIST, HttpStatus.BAD_REQUEST, email);
    }

}
