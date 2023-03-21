package com.example.coursach.repository;

import com.example.coursach.entity.message.ErrorMessage;
import com.example.coursach.entity.message.MessageId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ErrorMessageRepository extends CrudRepository<ErrorMessage, MessageId> {

    List<ErrorMessage> findAllByIdInOrderByMessageAsc(List<MessageId> id);

}
