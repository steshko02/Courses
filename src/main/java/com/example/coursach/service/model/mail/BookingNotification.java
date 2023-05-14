package com.example.coursach.service.model.mail;

import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingNotification extends Notification {

    private final String courseTitle;

    private final String receiverFullName;
    private final String action;
    private final String courseInfo;

    public BookingNotification(MessageLocale localisation,
                               MailScope mailScope,
                               String consumerEmail,
                               String courseTitle,
                               String receiverFullName,
                               String action,
                               String courseInfo) {
        super(mailScope, consumerEmail, localisation);
        this.receiverFullName = receiverFullName;
        this.courseTitle = courseTitle;
        this.action = action;
        this.courseInfo = courseInfo;
    }
}
