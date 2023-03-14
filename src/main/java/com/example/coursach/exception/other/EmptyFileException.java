package com.example.coursach.exception.other;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class EmptyFileException extends MessageWithErrorCodeException {

    public EmptyFileException(String... args) {
        super(ErrorCode.FILE_IS_EMPTY, HttpStatus.BAD_REQUEST, args);
    }

}
