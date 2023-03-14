package com.example.coursach.exception.photo;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class WrongPhotoFormatException extends MessageWithErrorCodeException {
    public WrongPhotoFormatException(String... args) {
        super(ErrorCode.WRONG_FORMAT, HttpStatus.BAD_REQUEST, args);
    }
}
