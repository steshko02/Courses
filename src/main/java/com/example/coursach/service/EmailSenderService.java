package com.example.coursach.service;

import com.example.coursach.service.model.mail.RecoveryNotification;
import eu.senla.git.coowning.service.model.mail.Notification;
import eu.senla.git.coowning.service.model.mail.enums.MailScope;
import eu.senla.git.coowning.service.model.mail.template.MailTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailSenderService {

    private final JavaMailSender postman;

    private final Map<MailScope, MailTemplate> mailTemplates = new HashMap<>();

    public EmailSenderService(JavaMailSender postman) {
        this.postman = postman;
    }

    @Async
    public void send(RecoveryNotification notification) {
        final MailTemplate currentTemplate = Optional.ofNullable(mailTemplates.get(notification.getMailScope()))
                        .orElseThrow(InvalidParameterException::new);

        postman.send(currentTemplate.construct(notification));
    }

    public void register(MailTemplate template) {
        mailTemplates.put(template.getMailScope(), template);
    }

}
