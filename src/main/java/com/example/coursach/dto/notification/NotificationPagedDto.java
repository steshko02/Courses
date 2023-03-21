package com.example.coursach.dto.notification;

import com.example.coursach.dto.pagable.PagingDtoList;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@Jacksonized
public class NotificationPagedDto {

    private final PagingDtoList paging;

    private final List<NotificationDto> notifications;

}
