package com.example.coursach.exception.user;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class WeakPasswordException extends MessageWithErrorCodeException {

    public WeakPasswordException(String regex) {
        super(ErrorCode.WEAK_PASSWORD, HttpStatus.BAD_REQUEST, regex);
    }

}
