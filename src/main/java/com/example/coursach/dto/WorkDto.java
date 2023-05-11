package com.example.coursach.dto;

import com.example.coursach.entity.enums.TimeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.List;

import static com.example.coursach.config.DateTimeFormat.ZONE_DATE_TIME_PATTEN;

@Getter
@Builder
@Jacksonized
public class WorkDto {
    private final Long id;
    private final String title;
    private final String description;
    private final TimeStatus status;
    private final String taskUrl;
    private final Long lessonId;
    private  final List<ResourceDto> resource;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime deadline;
}
