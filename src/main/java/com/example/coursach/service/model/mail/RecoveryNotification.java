package com.example.coursach.service.model.mail;

import eu.senla.git.coowning.entity.message.MessageLocale;
import eu.senla.git.coowning.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecoveryNotification extends Notification {

    private final String username;

    private final Integer recoveryCode;

    public RecoveryNotification(MailScope mailScope, String consumerEmail, MessageLocale localisation,
                                String username, Integer recoveryCode) {
        super(mailScope, consumerEmail, localisation);
        this.username = username;
        this.recoveryCode = recoveryCode;
    }

}
