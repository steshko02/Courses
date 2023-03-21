package com.example.coursach.service.model.mail;

import eu.senla.git.coowning.entity.message.MessageLocale;
import eu.senla.git.coowning.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Notification {

    private final MailScope mailScope;

    private final String consumerEmail;

    private final MessageLocale localisation;

    public static InvitationNotification buildInvitationNotification(MessageLocale localisation,
                                                                     MailScope mailScope,
                                                                     String consumerEmail,
                                                                     String objectInfo,
                                                                     String invitationCode,
                                                                     String receiverFullName,
                                                                     String itemOwnerFullName) {
        return new InvitationNotification(localisation, mailScope, consumerEmail,
                objectInfo, invitationCode, receiverFullName, itemOwnerFullName);
    }

    public static RecoveryNotification buildRecoveryNotification(MessageLocale localisation,
                                                                 MailScope mailScope,
                                                                 String consumerEmail,
                                                                 String username,
                                                                 Integer code) {
        return new RecoveryNotification(mailScope, consumerEmail, localisation, username, code);
    }

    public static GreetingsNotification buildGreetingsNotification(MessageLocale localisation,
                                                                   MailScope mailScope,
                                                                   String consumerEmail,
                                                                   String code) {
        return new GreetingsNotification(mailScope, consumerEmail, localisation, code);
    }

    public static ReminderNotification buildReminderNotification(MessageLocale localisation,
                                                                 MailScope mailScope,
                                                                 String consumerEmail,
                                                                 String username,
                                                                 String objectInfo,
                                                                 String time) {
        return new ReminderNotification(mailScope, consumerEmail, localisation, objectInfo, username, time);
    }

}
