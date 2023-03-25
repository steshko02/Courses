package com.example.coursach.service.model.mail.template.impl;

import com.example.coursach.config.properties.MailProperties;
import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.ReminderNotification;
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
public class RemindingTemplate implements MailTemplate {

    private final TemplateUtils templateUtils;
    private final MailProperties mailProperties;

    private final MailScope mailScope = MailScope.REMINDING;

    private final Map<MessageLocale, String> letterTemplates = new HashMap<>();

    public RemindingTemplate(TemplateUtils templateUtils, MailProperties mailProperties) {
        this.templateUtils = templateUtils;
        this.mailProperties = mailProperties;
    }

    @Override
    public MailScope getMailScope() {
        return mailScope;
    }

    @Override
    public SimpleMailMessage construct(Notification notification) {
        final ReminderNotification recoveryNotification = (ReminderNotification) notification;

        final String letterText = templateUtils.constructLetter(
                letterTemplates.get(notification.getLocalisation()),
                Map.of(
                        "{user_name}", recoveryNotification.getNickname(),
                        "{objectInfo}", recoveryNotification.getObjectInfo(),
                        "{time_before_booking}", recoveryNotification.getTimeBeforeBooking()
                ));

        SimpleMailMessage simpleMailMessage = buildSimpleMailMessage();
        simpleMailMessage.setText(letterText);
        simpleMailMessage.setTo(notification.getConsumerEmail());

        return simpleMailMessage;
    }

    private SimpleMailMessage buildSimpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailProperties.getMailFrom());
        simpleMailMessage.setSubject(String.format(Patterns.COURSES_NAMING_PATTERN, "REMINDING"));

        return simpleMailMessage;
    }

    @Override
    @PostConstruct
    public void templatesInitialization() {
        EnumSet.allOf(MessageLocale.class).forEach(locale -> letterTemplates.put(locale,
                templateUtils.getLetterTemplate(mailScope, locale)));
    }
}
