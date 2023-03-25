package com.example.coursach.service.converter;

import com.example.coursach.dto.notification.NotificationDto;
import com.example.coursach.dto.notification.NotificationPagedDto;
import com.example.coursach.dto.pagable.PagingDtoList;
import com.example.coursach.service.converter.resolvers.LocalizationResolver;
import com.example.coursach.service.converter.resolvers.urlresolver.UrlResolver;
import com.example.coursach.entity.notification.Notification;
import com.example.coursach.service.utils.ZonedDateTimeUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationConverter {

    public NotificationDto toDto(
            Notification notification, LocalizationResolver localizationResolver, Map<String, UrlResolver> urlResolverMap) {
        UrlResolver urlResolver = urlResolverMap.get(notification.getType());
        return NotificationDto
                .builder()
                .uuid(notification.getUuid())
                .type(notification.getType())
                .title(notification.getTitle())
                .date(ZonedDateTimeUtils.addUtcZone(notification.getDate()))
                .text(localizationResolver.getText(notification.getType()))
                .actionUrl(urlResolver.getUrl(notification))
                .iconUrl(urlResolver.getPhotoUrl(notification))
                .build();
    }

    public List<NotificationDto> toNotificationDtos(
            List<Notification> notifications,
            LocalizationResolver localizationResolver,
            Map<String, UrlResolver> urlResolverMap) {
        return notifications
                .stream()
                .map(n -> toDto(n, localizationResolver, urlResolverMap))
                .collect(Collectors.toList());
    }

    public NotificationPagedDto bookingsPagedDto(
            int totalPages, int totalElements, int number,
            List<Notification> notifications,
            LocalizationResolver localizationResolver, Map<String, UrlResolver> urlResolverMap) {
        return NotificationPagedDto
                .builder()
                .paging(
                        PagingDtoList
                                .builder()
                                .totalPages(totalPages)
                                .totalCount(totalElements)
                                .pageNumber(number + 1)
                                .build()
                )
                .notifications(toNotificationDtos(notifications, localizationResolver, urlResolverMap))
                .build();
    }

}
