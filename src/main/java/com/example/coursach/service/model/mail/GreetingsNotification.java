package com.example.coursach.service.model.mail;

import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GreetingsNotification extends Notification {

    private final String greetingsCode;

    public GreetingsNotification(MailScope mailScope,
                                 String consumerEmail,
                                 MessageLocale localisation,
                                 String greetingsCode) {
        super(mailScope, consumerEmail, localisation);
        this.greetingsCode = greetingsCode;
    }
}
