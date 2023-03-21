package com.example.coursach.dto.notification;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

import static com.example.coursach.config.DateTimeConfig.ZONE_DATE_TIME_PATTEN;

@Getter
@Jacksonized
@Builder
public class NotificationDto {

    private final Long id;

    private final String type;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime date;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String actionUrl;

    private final String iconUrl;

    private final String title;

    private final String text;

}
