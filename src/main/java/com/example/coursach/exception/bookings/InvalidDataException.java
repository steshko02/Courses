package com.example.coursach.exception.bookings;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidDataException extends MessageWithErrorCodeException {

    public InvalidDataException(String... args) {
        super(ErrorCode.INVALID_DATA, HttpStatus.BAD_REQUEST, args);
    }

}
