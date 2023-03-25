package com.example.coursach.exception.user;
import com.example.coursach.exception.ErrorCode;
import com.example.coursach.exception.MessageWithErrorCodeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class CommonInvalidFieldException extends MessageWithErrorCodeException {

    private final List<InvalidFieldException> fieldExceptions;

    public CommonInvalidFieldException(List<InvalidFieldException> fieldExceptions) {
        super(ErrorCode.FIELD_NOT_FOUND, HttpStatus.BAD_REQUEST, "");
        this.fieldExceptions = fieldExceptions;
    }

}
