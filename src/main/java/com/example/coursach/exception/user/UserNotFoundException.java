package com.example.coursach.exception.user;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends MessageWithErrorCodeException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND, HttpStatus.FORBIDDEN);
    }

}
