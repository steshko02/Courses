package com.example.coursach.exception.photo;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class WrongPhotoFormatException extends MessageWithErrorCodeException {
    public WrongPhotoFormatException(String... args) {
        super(ErrorCode.WRONG_FORMAT, HttpStatus.BAD_REQUEST, args);
    }
}
