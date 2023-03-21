package com.example.coursach.service.converter.resolvers.urlresolver;

import com.example.coursach.service.model.mail.Notification;

import java.util.List;

public interface UrlResolver {

    String getUrl(Notification notification);

    String getPhotoUrl(Notification notification);

    List<String> getNotificationType();

}
