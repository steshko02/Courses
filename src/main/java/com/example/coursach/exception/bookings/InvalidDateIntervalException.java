package com.example.coursach.exception.bookings;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class InvalidDateIntervalException extends MessageWithErrorCodeException {

    public InvalidDateIntervalException() {
        super(ErrorCode.INVALID_DATE_INTERVAL, HttpStatus.BAD_REQUEST);
    }

}
