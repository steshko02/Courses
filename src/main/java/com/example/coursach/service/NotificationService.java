package com.example.coursach.service;

import com.example.coursach.dto.notification.NotificationPagedDto;
import com.example.coursach.dto.notification.NotificationRequestParamsDto;
import com.example.coursach.entity.notification.Notification;
import com.example.coursach.exception.notifications.NotificationNotFoundException;
import com.example.coursach.repository.NotificationRepository;
import com.example.coursach.service.converter.NotificationConverter;
import com.example.coursach.service.converter.resolvers.urlresolver.UrlResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationConverter notificationConverter;

    private final LocalMessageService localMessageService;

    private final Map<String, UrlResolver> urlResolverMap;

    public NotificationService(
            NotificationRepository notificationRepository,
            NotificationConverter notificationConverter,
            LocalMessageService localMessageService,
            @Qualifier("urlResolverMap") Map<String, UrlResolver> urlResolverMap) {
        this.notificationRepository = notificationRepository;
        this.notificationConverter = notificationConverter;
        this.localMessageService = localMessageService;
        this.urlResolverMap = urlResolverMap;
    }

    public void createNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public NotificationPagedDto getAll(NotificationRequestParamsDto paramsDto, String userUuid) {
//        Page<Notification> notifications =
//                notificationRepository.getAllNotificationsOfUser(
//                        userUuid,
//                        PageRequest.of(paramsDto.getPage().getNumber() - 1, paramsDto.getPage().getSize())
//                );
//
//        LocalizationResolver localizationResolver =
//                notificationTitle -> localMessageService.getByCodeId(notificationTitle).getMessage();
//
//        return notificationConverter.bookingsPagedDto(
//                notifications.getTotalPages(),
//                (int) notifications.getTotalElements(),
//                notifications.getNumber(),
//                notifications.getContent(),
//                localizationResolver,
//                urlResolverMap
//        );
        return null;
    }

    @Transactional
    public void delete(String id, String userUuid) {

        Notification notification = notificationRepository.findByUserUuidAndNotificationUuid(userUuid, id)
                .orElseThrow(NotificationNotFoundException::new);

        if (notification.getUsers().size() <= 1) {
            notificationRepository.delete(notification);
            return;
        }

        notification.getUsers().removeIf(u -> u.getId().equals(userUuid));
        notificationRepository.save(notification);
    }
}
