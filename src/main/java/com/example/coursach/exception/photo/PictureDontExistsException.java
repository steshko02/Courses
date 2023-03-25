package com.example.coursach.exception.photo;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class PictureDontExistsException extends MessageWithErrorCodeException {
    public PictureDontExistsException(String... args) {
        super(ErrorCode.INVALID_DATA, HttpStatus.BAD_REQUEST, args);
    }
}
