package com.example.coursach.exception.profile;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class ProfileAlreadyExistException extends MessageWithErrorCodeException {

    public ProfileAlreadyExistException() {
        super(ErrorCode.PROFILE_ALREADY_EXIST, HttpStatus.BAD_REQUEST, "");
    }

}
