package com.example.coursach.service.model.mail.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailScope {

    INVITATION("invitation-{locale}"),

    GREETINGS("greetings-{locale}"),

    RECOVERY("recovery-{locale}"),

    REMINDING("reminding-{locale}");


    private final String templateLocation;

}
