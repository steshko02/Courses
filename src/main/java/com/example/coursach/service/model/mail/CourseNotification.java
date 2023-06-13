package com.example.coursach.service.model.mail;

import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseNotification extends Notification{

    private final String courseTitle;

    private final String receiverFullName;
    private final String date;


    public CourseNotification(MessageLocale localisation, MailScope mailScope, String consumerEmail, String courseTitle, String receiverFullName, String date) {
        super(mailScope, consumerEmail, localisation);
        this.receiverFullName = receiverFullName;
        this.courseTitle = courseTitle;
        this.date = date;
    }
}
