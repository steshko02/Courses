package com.example.coursach.entity.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "error_messages")
public class ErrorMessage {

    @EmbeddedId
    private MessageId id;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private int status;

}
