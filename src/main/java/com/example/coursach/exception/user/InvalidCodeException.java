package com.example.coursach.exception.user;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidCodeException extends MessageWithErrorCodeException {

    public InvalidCodeException() {
        super(ErrorCode.INVALID_CODE, HttpStatus.BAD_REQUEST, "");
    }

}
