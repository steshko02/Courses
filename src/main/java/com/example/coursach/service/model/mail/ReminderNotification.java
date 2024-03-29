package com.example.coursach.service.model.mail;

import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReminderNotification extends Notification {

    private final String objectInfo;

    private final String nickname;
    private final String timeBeforeBooking;

    public ReminderNotification(MailScope mailScope,
                                String consumerEmail,
                                MessageLocale localisation,
                                String objectInfo,
                                String nickname,
                                String timeBeforeBooking) {
        super(mailScope, consumerEmail, localisation);
        this.objectInfo = objectInfo;
        this.nickname = nickname;
        this.timeBeforeBooking = timeBeforeBooking;
    }
}
