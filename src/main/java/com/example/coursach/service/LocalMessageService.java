package com.example.coursach.service;


import eu.senla.git.coowning.entity.message.LocalMessage;
import eu.senla.git.coowning.entity.message.MessageId;
import eu.senla.git.coowning.repository.LocalMessageRepository;
import eu.senla.git.coowning.service.model.LocalMessageCodes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class LocalMessageService {

    private final LocalMessageRepository messageRepository;

    private final CurrentUserRequestLocaleService requestLocaleService;

    public LocalMessageService(LocalMessageRepository messageRepository, CurrentUserRequestLocaleService requestLocaleService) {
        this.messageRepository = messageRepository;
        this.requestLocaleService = requestLocaleService;
    }


    public Map<String, String> getMessages(List<String> messageCodes) {

        List<MessageId> messageIds = messageCodes.stream()
                .map(code -> MessageId.builder().code(code).locale(requestLocaleService.getCurrentLocale()).build())
                .collect(Collectors.toList());

        Map<String, String> existingCodesMap = messageRepository.findAllByIdInOrderByMessageAsc(messageIds)
                .stream()
                .collect(
                        Collectors.toMap(
                                x -> x.getId().getCode(),
                                LocalMessage::getMessage
                        )
                );

        return messageCodes.stream().collect(
                Collectors.toMap(
                        Function.identity(),
                        code -> existingCodesMap.getOrDefault(code, code)
                )
        );
    }

    public String getMessage(LocalMessageCodes localMessageCode) {
        MessageId messageId = MessageId.builder()
                .code(localMessageCode.getCode())
                .locale(requestLocaleService.getCurrentLocale())
                .build();

        LocalMessage localMessage = messageRepository.findById(messageId).orElseThrow(RuntimeException::new);
        return localMessage.getMessage();
    }

    public LocalMessage getByCodeId(String codeId) {
        MessageId messageId = MessageId.builder()
                .code(codeId)
                .locale(requestLocaleService.getCurrentLocale())
                .build();
        return messageRepository.findById(messageId).orElseThrow(RuntimeException::new);
    }
}
