package com.example.coursach.exception.profile;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class ProfileAlreadyExistException extends MessageWithErrorCodeException {

    public ProfileAlreadyExistException() {
        super(ErrorCode.PROFILE_ALREADY_EXIST, HttpStatus.BAD_REQUEST, "");
    }

}
