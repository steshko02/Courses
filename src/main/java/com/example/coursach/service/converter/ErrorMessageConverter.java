package com.example.coursach.service.converter;

import com.example.coursach.dto.error.ErrorMessageDto;
import com.example.coursach.entity.message.ErrorMessage;
import com.example.coursach.service.converter.resolvers.ErrorMessagesArgsResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ErrorMessageConverter {

    public ErrorMessageDto toDto(ErrorMessage errorMessage, String... args) {
        return ErrorMessageDto
                .builder()
                .status(HttpStatus.valueOf(errorMessage.getStatus()))
                .message(MessageFormat.format(errorMessage.getMessage(), args))
                .code(errorMessage.getId().getCode())
                .build();
    }

    public List<ErrorMessageDto> toListDto(List<ErrorMessage> errorMessage, ErrorMessagesArgsResolver messagesArgsResolver) {
        return errorMessage
                .stream()
                .map(e -> toDto(e, messagesArgsResolver.getArgsByMessageId(e.getId())))
                .collect(Collectors.toList());
    }

}
