package com.example.coursach.dto;

import com.example.coursach.entity.enums.TimeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class CourseShortInfoDto {

    private final Long id;
    private final String title;
    private final TimeStatus status;
    private final Integer size;
    private final Integer count;

}
