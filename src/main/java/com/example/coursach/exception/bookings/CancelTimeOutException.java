package com.example.coursach.exception.bookings;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class CancelTimeOutException extends MessageWithErrorCodeException {

    public CancelTimeOutException() {
        super(ErrorCode.CANCEL_TIME_OUT_EXCEPTION, HttpStatus.FORBIDDEN, "");
    }

}
