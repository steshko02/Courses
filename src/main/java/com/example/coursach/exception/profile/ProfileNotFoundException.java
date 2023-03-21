package com.example.coursach.exception.profile;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class ProfileNotFoundException extends MessageWithErrorCodeException {

    public ProfileNotFoundException() {
        super(ErrorCode.PROFILE_NOT_FOUND, HttpStatus.NOT_FOUND, "");
    }

}
