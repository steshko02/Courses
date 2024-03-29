package com.example.coursach.service.model.mail.template;

import com.example.coursach.service.EmailSenderService;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public interface MailTemplate {

    MailScope getMailScope();

    SimpleMailMessage construct(Notification notification);


    /**
     * Attention! No need to override.
     */
    @Autowired
    default void lookup(EmailSenderService senderService) {
        senderService.register(this);
    }

    /**
     * Method to initialize mail templates. Look for example at InvitationTemplate class.
     */
    void templatesInitialization();

}
