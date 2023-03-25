package com.example.coursach.service.model.mail.template.impl;

import com.example.coursach.config.properties.MailProperties;
import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.InvitationNotification;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import com.example.coursach.service.model.mail.template.MailTemplate;
import com.example.coursach.service.model.mail.utils.TemplateUtils;
import com.example.coursach.storage.pattern.Patterns;
import lombok.Getter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class InvitationTemplate implements MailTemplate {

    private final TemplateUtils templateUtils;

    private final MailProperties mailProperties;

    private final MailScope mailScope = MailScope.INVITATION;

    private final Map<MessageLocale, String> letterTemplates = new HashMap<>();

    public InvitationTemplate(TemplateUtils templateUtils, MailProperties mailProperties) {
        this.templateUtils = templateUtils;
        this.mailProperties = mailProperties;
    }

    @Override
    @PostConstruct
    public void templatesInitialization() {
        EnumSet.allOf(MessageLocale.class).forEach(locale -> letterTemplates.put(locale,
                templateUtils.getLetterTemplate(mailScope, locale)));
    }

    @Override
    public SimpleMailMessage construct(Notification notification) {
        final InvitationNotification invitationNotification = (InvitationNotification) notification;

        final String letterText = templateUtils.constructLetter(
                letterTemplates.get(notification.getLocalisation()),
                Map.of(
                        "{potential_user_name}", invitationNotification.getReceiverFullName(),
                        "{objectInfo}", invitationNotification.getObjectInfo(),
                        "{owner_name}", invitationNotification.getItemOwnerFullName(),
                        "{invitation_link}", "",
                        "{6_digital_code}", invitationNotification.getInvitationCode()
                )
        );

        SimpleMailMessage simpleMailMessage = buildSimpleMailMessage();
        simpleMailMessage.setText(letterText);
        simpleMailMessage.setTo(notification.getConsumerEmail());

        return simpleMailMessage;
    }

    private SimpleMailMessage buildSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getMailFrom());
        simpleMailMessage.setSubject(String.format(Patterns.COURSES_NAMING_PATTERN, "INVITATION"));

        return simpleMailMessage;
    }

}
