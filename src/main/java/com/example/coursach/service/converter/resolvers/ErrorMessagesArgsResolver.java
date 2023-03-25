package com.example.coursach.service.converter.resolvers;

import com.example.coursach.entity.message.MessageId;

@FunctionalInterface
public interface ErrorMessagesArgsResolver {

    String[] getArgsByMessageId(MessageId messageId);

}
