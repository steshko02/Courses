package com.example.coursach.dto;

import com.example.coursach.entity.enums.ReportStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ReportCreateDto {
    private final Long id;
    private final String answerUrl;
    private final String lectorId;
    private final String userId;
    private final Long workId;
    private final ReportStatus reportStatus;
}
