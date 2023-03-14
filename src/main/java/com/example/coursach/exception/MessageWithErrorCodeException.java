package com.example.coursach.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class MessageWithErrorCodeException extends RuntimeException {

    private final HttpStatus status;

    private final ErrorCode code;

    private final String[] args;

    public MessageWithErrorCodeException(ErrorCode code, HttpStatus status, String... args) {
        this.status = status;
        this.code = code;
        this.args = args;
    }

}
