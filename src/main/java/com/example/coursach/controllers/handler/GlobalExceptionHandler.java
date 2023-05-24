package com.example.coursach.controllers.handler;


import com.example.coursach.dto.error.ErrorMessageDto;
import com.example.coursach.entity.message.ErrorMessage;
import com.example.coursach.entity.message.MessageId;
import com.example.coursach.exception.MessageWithErrorCodeException;
import com.example.coursach.exception.user.CommonInvalidFieldException;
import com.example.coursach.service.ErrorMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

@Slf4j
@RestControllerAdvice
@EnableWebMvc
public class GlobalExceptionHandler {

    private final ErrorMessageService messageService;

    public GlobalExceptionHandler(ErrorMessageService messageService) {
        this.messageService = messageService;
    }

    @ExceptionHandler({MessageWithErrorCodeException.class})
    protected ResponseEntity<ErrorMessageDto> handle(MessageWithErrorCodeException exception) {
        ErrorMessageDto errorResponseDto =
                messageService.getMessage(exception);

        return new ResponseEntity<>(errorResponseDto, errorResponseDto.getStatus());
    }

    @ExceptionHandler({CommonInvalidFieldException.class})
    protected ResponseEntity<List<ErrorMessageDto>> handle(CommonInvalidFieldException exception) {
        List<ErrorMessageDto> errorResponseDto =
                messageService.getMessage(exception);

        return new ResponseEntity<>(errorResponseDto, errorResponseDto.get(0).getStatus());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<List<ErrorMessageDto>> handleValid(MethodArgumentNotValidException exception) {
        List<ErrorMessageDto> errorResponseDto = messageService
                .getMessageFromMethodArgumentNotValidException(exception);

        return new ResponseEntity<>(errorResponseDto, errorResponseDto.get(0).getStatus());
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    protected ResponseEntity<ErrorMessageDto> handle(AccessDeniedException exception) {
        String code = "access.denied";
        ErrorMessageDto errorResponseDto = ErrorMessageDto.builder()
                        .code(code)
                        .message("Access is denied!")
                        .status(HttpStatus.FORBIDDEN)
                        .build();

        return new ResponseEntity<>(errorResponseDto, errorResponseDto.getStatus());
    }
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorMessageDto> handleError(Exception exception) {
        log.error("Smth went wrong: ", exception);
        return ResponseEntity.internalServerError()
                .body(ErrorMessageDto.builder()
                        .code("unexpected.error")
                        .message("We are already fixing it")
                        .build()
                );
    }

}
