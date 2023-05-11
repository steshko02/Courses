package com.example.coursach.dto;

import com.example.coursach.entity.enums.TimeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LessonShortInfoDto {
    private final Long id;
    private final String title;

//    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
//    private final ZonedDateTime dateEnd;
//    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
//    private final ZonedDateTime dateStart;

    private final Integer order;

    private TimeStatus status;
}
