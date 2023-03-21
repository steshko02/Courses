package com.example.coursach.exception.user;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidUsernameOrPasswordException extends MessageWithErrorCodeException {

    public InvalidUsernameOrPasswordException() {
        super(ErrorCode.INVALID_EMAIL_OR_PASSWORD, HttpStatus.UNAUTHORIZED);
    }
}
