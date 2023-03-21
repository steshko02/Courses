package com.example.coursach.exception.other;

import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import org.springframework.http.HttpStatus;

public class EmptyFileException extends MessageWithErrorCodeException {

    public EmptyFileException(String... args) {
        super(ErrorCode.FILE_IS_EMPTY, HttpStatus.BAD_REQUEST, args);
    }

}
