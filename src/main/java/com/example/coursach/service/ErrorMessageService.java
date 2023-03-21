package com.example.coursach.service;

import eu.senla.git.coowning.dto.error.ErrorMessageDto;
import eu.senla.git.coowning.entity.message.ErrorMessage;
import eu.senla.git.coowning.entity.message.MessageId;
import eu.senla.git.coowning.entity.message.MessageLocale;
import eu.senla.git.coowning.exception.MessageWithErrorCodeException;
import eu.senla.git.coowning.exception.shareditem.CommonInvalidFieldException;
import eu.senla.git.coowning.repository.ErrorMessageRepository;
import eu.senla.git.coowning.service.converter.ErrorMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ErrorMessageService {

    private final CurrentUserRequestLocaleService localeService;

    private final ErrorMessageRepository messageRepository;

    private final ErrorMessageConverter converter;

    public ErrorMessageService(
            CurrentUserRequestLocaleService localeService,
            ErrorMessageRepository messageRepository,
            ErrorMessageConverter converter) {
        this.localeService = localeService;
        this.messageRepository = messageRepository;
        this.converter = converter;
    }

    public ErrorMessageDto getMessage(MessageWithErrorCodeException exception) {
        MessageId id = buildMessageId(exception.getCode().getCode(), localeService.getCurrentLocale());

        ErrorMessage errorMessage = messageRepository.findById(id)
                .orElse(getMessageExceptionNotFoundByCode(id.getCode()));

        return converter.toDto(errorMessage, exception.getArgs());
    }

    public List<ErrorMessageDto> getMessage(CommonInvalidFieldException exception) {
        List<MessageId> ids = new ArrayList<>();
        Map<MessageId, String[]> messageMap = new HashMap<>();

        exception.getFieldExceptions()
                .forEach(field -> {
                    MessageId messageId = buildMessageId(field.getCode().getCode(), localeService.getCurrentLocale());
                    ids.add(messageId);
                    messageMap.put(messageId, field.getArgs());
                });

        List<ErrorMessage> errorMessages = findSortedMessagesFromRepository(ids);

        return converter.toListDto(errorMessages, messageMap::get);
    }

    private MessageId buildMessageId(String code, MessageLocale locale) {
        return MessageId.builder()
                .code(code)
                .locale(locale)
                .build();
    }

    private ErrorMessage getMessageExceptionNotFoundByCode(String code) {
        return ErrorMessage.builder()
                .id(MessageId.builder().code(code).build())
                .status(500)
                .message("Text of error not found")
                .build();
    }

    public List<ErrorMessageDto> getMessageFromMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<MessageId> ids = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(field -> ids.add(buildMessageId(field.getDefaultMessage(), localeService.getCurrentLocale())));

        List<ErrorMessage> messages = findSortedMessagesFromRepository(ids);

        return converter.toListDto(messages, messageId -> new String[0]);
    }

    private List<ErrorMessage> findSortedMessagesFromRepository(List<MessageId> ids) {
        Map<MessageId, ErrorMessage> map = new LinkedHashMap<>();
        List<ErrorMessage> messages = new ArrayList<>();

        messageRepository.findAllByIdInOrderByMessageAsc(ids)
                .forEach(x -> {
                    map.put(x.getId(), x);
                    messages.add(x);
                });

        for (MessageId id : ids) {
            if (map.get(id) == null) {
                messages.add(getMessageExceptionNotFoundByCode(id.getCode()));
            }
        }

        return messages;

    }

}
