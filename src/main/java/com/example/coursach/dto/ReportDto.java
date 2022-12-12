package com.example.coursach.dto;

import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.entity.enums.ReportStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;

import static com.example.coursach.config.DateTimeConfig.ZONE_DATE_TIME_PATTEN;

@Getter
@Builder
@Jacksonized
public class ReportDto {

    private final String answerUrl;
    private final Long lectorId;
    private final Long userId;
    private final Long workId;
    private final ReportStatus reportStatus;
    private final String comments;
    private final Integer rating;
}
