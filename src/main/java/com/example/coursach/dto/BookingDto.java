package com.example.coursach.dto;

import com.example.coursach.entity.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

import static com.example.coursach.config.DateTimeConfig.ZONE_DATE_TIME_PATTEN;

@Getter
@Builder
@Jacksonized
public class BookingDto {

    private final Long userId;
    private final Long courseId;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateCreation;

    private final BookingStatus status;

}
