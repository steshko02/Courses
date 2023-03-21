package com.example.coursach.exception.user;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class WeakPasswordException extends MessageWithErrorCodeException {

    public WeakPasswordException(String regex) {
        super(ErrorCode.WEAK_PASSWORD, HttpStatus.BAD_REQUEST, regex);
    }

}
