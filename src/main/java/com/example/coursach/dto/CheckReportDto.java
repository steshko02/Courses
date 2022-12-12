package com.example.coursach.dto;

import com.example.coursach.entity.enums.ReportStatus;
import com.example.coursach.entity.enums.TimeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CheckReportDto {
    private final Long id;
    private final String comments;
    private final Integer rating;
    private final Long lessonId;
    private final Long userId;
    private final Long workId;

    private final ReportStatus reportStatus;

}
