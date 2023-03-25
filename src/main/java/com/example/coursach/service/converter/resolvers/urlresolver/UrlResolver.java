package com.example.coursach.service.converter.resolvers.urlresolver;

import com.example.coursach.entity.notification.Notification;

import java.util.List;

public interface UrlResolver {

    String getUrl(Notification notification);

    String getPhotoUrl(Notification notification);

    List<String> getNotificationType();

}
