package com.example.coursach.dto;

import com.example.coursach.entity.enums.TimeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.example.coursach.config.DateTimeFormat.ZONE_DATE_TIME_PATTEN;

@Getter
@Builder
@Jacksonized
public class AnswerDto {
    private final Long id;
    private final String comment;
    private final Long workId;
    private  final List<ResourceDto> resource;
    private final TimeStatus timeStatus;
    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime date;
}
