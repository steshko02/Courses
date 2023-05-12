package com.example.coursach.dto;

import com.example.coursach.dto.user.BaseUserInformationDto;
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
public class LessonInfoForMentorsDto {

    private final Long id;

    private final String title;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateEnd;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateStart;

    private TimeStatus status;

    private final List<BaseUserInformationDto> mentors;
}
