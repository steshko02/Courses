package com.example.coursach.dto;

import com.example.coursach.dto.user.BaseUserInformationDto;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Getter
@Builder
@Jacksonized
public class CheckWorkDto {
    private final Long id;
    private final String comment;
    private final Long answerId;
    private final LocalDateTime date;
    private final Integer mark;
    private final BaseUserInformationDto mentor;
}
