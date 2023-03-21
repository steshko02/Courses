package com.example.coursach.exception.photo;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class PictureDontExistsException extends MessageWithErrorCodeException {
    public PictureDontExistsException(String... args) {
        super(ErrorCode.INVALID_DATA, HttpStatus.BAD_REQUEST, args);
    }
}
