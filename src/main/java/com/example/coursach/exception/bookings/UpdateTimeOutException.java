package com.example.coursach.exception.bookings;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class UpdateTimeOutException extends MessageWithErrorCodeException {

    public UpdateTimeOutException() {
        super(ErrorCode.UPDATE_TIME_OUT_EXCEPTION, HttpStatus.FORBIDDEN, "");
    }

}
