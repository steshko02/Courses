package com.example.coursach.repository;

import com.example.coursach.entity.message.LocalMessage;
import com.example.coursach.entity.message.MessageId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface LocalMessageRepository extends CrudRepository<LocalMessage, MessageId> {

    List<LocalMessage> findAllByIdInOrderByMessageAsc(List<MessageId> ids);
    Optional<LocalMessage> findById(MessageId id);
}
