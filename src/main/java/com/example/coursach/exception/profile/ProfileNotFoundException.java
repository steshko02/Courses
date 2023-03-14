package com.example.coursach.exception.profile;

import eu.senla.git.coowning.exception.ErrorCode;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends MessageWithErrorCodeException {

    public ProfileNotFoundException() {
        super(ErrorCode.PROFILE_NOT_FOUND, HttpStatus.NOT_FOUND, "");
    }

}
