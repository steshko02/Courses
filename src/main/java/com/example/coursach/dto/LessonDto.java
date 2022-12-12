package com.example.coursach.dto;

import com.example.coursach.entity.enums.TimeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class LessonDto {
    private final Long id;
    private final String title;
    private final String description;
    private final TimeStatus status;
    private final String sourceUrl;
    private final Long courseId;
    private final Integer duration;
    private final Long resourceId;
}
