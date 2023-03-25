package com.example.coursach.exception.user;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidFieldException extends MessageWithErrorCodeException {

    public InvalidFieldException(String code, String... args) {
        super(ErrorCode.lookup(code), HttpStatus.BAD_REQUEST, args);
    }
}
