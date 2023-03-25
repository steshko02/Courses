package com.example.coursach.service.converter.resolvers.urlresolver;

import com.example.coursach.config.properties.ApplicationUrlProperties;
import com.example.coursach.controllers.UserController;
import com.example.coursach.entity.enums.NotificationType;
import com.example.coursach.entity.notification.UserAcceptInvitationNotification;
import com.example.coursach.service.MinioStorageService;
import org.springframework.stereotype.Component;
import com.example.coursach.entity.notification.Notification;

import java.util.List;

@Component
public class UserGetUrlResolver implements UrlResolver {

    private final ApplicationUrlProperties applicationUrlProperties;

    private final MinioStorageService minioStorageService;

    public UserGetUrlResolver(ApplicationUrlProperties applicationUrlProperties, MinioStorageService minioStorageService) {
        this.applicationUrlProperties = applicationUrlProperties;
        this.minioStorageService = minioStorageService;
    }

    @Override
    public String getUrl(Notification notification) {
        return String.format("%s%s/%s",
                applicationUrlProperties.getAppUrl(),
                UserController.USERS_PATH,
                ((UserAcceptInvitationNotification) notification).getRefUser().getId()
        );
    }

    @Override
    public String getPhotoUrl(Notification notification) {
        return minioStorageService.getPictureUrl(
                ((UserAcceptInvitationNotification) notification).getRefUser()
        );
    }

    @Override
    public List<String> getNotificationType() {
        return List.of(NotificationType.USER_ACCEPT_INVITATION);
    }

}
