package com.example.coursach.dto;

import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.enums.CourseType;
import com.example.coursach.entity.enums.TimeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.time.ZonedDateTime;
import java.util.List;

import static com.example.coursach.config.DateTimeFormat.ZONE_DATE_TIME_PATTEN;

@Getter
@Setter
@Builder
@Jacksonized
public class CourseDtoWithMentors {

    private final Long id;
    private final String title;
    private final String description;
    private final CourseType type;
    private final TimeStatus status;
    private final Integer size;
    private final Integer busy;

    private final List<BaseUserInformationDto> mentors;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateEnd;

    @JsonFormat(pattern = ZONE_DATE_TIME_PATTEN)
    private final ZonedDateTime dateStart;

    private  final ResourceDto resource;

    private List<LessonShortInfoDto> lessons;
    private String studentId;
    private String mentorId;

}
