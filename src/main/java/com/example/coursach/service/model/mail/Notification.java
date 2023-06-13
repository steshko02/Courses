package com.example.coursach.service.model.mail;

import com.example.coursach.entity.message.MessageLocale;
import com.example.coursach.service.model.mail.enums.MailScope;
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

    public static BookingNotification buildBookingNotification(MessageLocale localisation,
                                                                MailScope mailScope,
                                                                String consumerEmail,
                                                                String courseTitle,
                                                                String receiverFullName,
                                                               String action,
                                                               String courseInfo) {
        return new BookingNotification(localisation, mailScope, consumerEmail, courseTitle, receiverFullName,action, courseInfo);
    }


    public static LessonNotification buildLessonNotification(MessageLocale localisation,
                                                               MailScope mailScope,
                                                               String consumerEmail,
                                                               String courseTitle,
                                                               String receiverFullName,
                                                               String lessonTitle,
                                                               String date) {
        return new LessonNotification(localisation, mailScope, consumerEmail, courseTitle, receiverFullName,lessonTitle, date);
    }

    public static Notification buildCourseNotification(MessageLocale currentLocale, MailScope courseStart, String email, String title,String user, String dateString) {
        return new CourseNotification(currentLocale, courseStart, email, title, user, dateString);
    }
}
