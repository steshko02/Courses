package com.example.coursach.service.model.mail;

import eu.senla.git.coowning.entity.message.MessageLocale;
import eu.senla.git.coowning.service.model.mail.enums.MailScope;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvitationNotification extends Notification {

    private final String objectInfo;

    private final String invitationCode;

    private final String receiverFullName;

    private final String itemOwnerFullName;

    public InvitationNotification(MessageLocale localisation,
                                  MailScope mailScope,
                                  String consumerEmail,
                                  String objectInfo,
                                  String invitationCode,
                                  String receiverFullName,
                                  String itemOwnerFullName) {
        super(mailScope, consumerEmail, localisation);
        this.objectInfo = objectInfo;
        this.invitationCode = invitationCode;
        this.receiverFullName = receiverFullName;
        this.itemOwnerFullName = itemOwnerFullName;
    }
}
