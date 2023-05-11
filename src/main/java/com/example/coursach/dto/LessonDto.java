package com.example.coursach.dto;

import com.example.coursach.dto.user.BaseUserInformationDto;
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
public class LessonDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Long courseId;
    private final List<String> userIds;
    private final List<BaseUserInformationDto> mentors;
    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateEnd;
    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateStart;
    private final Integer order;
}
