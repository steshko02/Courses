package com.example.coursach.entity.notification;

import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.NotificationType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@DiscriminatorValue(NotificationType.USER_ACCEPT_INVITATION)
public class UserAcceptInvitationNotification extends Notification {

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "ref_user_uuid", referencedColumnName = "id")
    private User refUser;

    @Builder
    public UserAcceptInvitationNotification(String uuid, LocalDateTime date, String type, Set<User> users, User refUser, String title) {
        super(uuid, date, type, users, title);
        this.refUser = refUser;
    }

}
