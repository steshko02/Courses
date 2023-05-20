package com.example.coursach.dto;

import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.enums.TimeStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Jacksonized
public class AnswerWithUserDto {
    private final Long id;
    private final String comment;
    private final Long workId;
    private final List<ResourceDto> resource;
    private final TimeStatus timeStatus;
    private final LocalDateTime date;
    private final BaseUserInformationDto user;
    private final CheckWorkDto result;
    private final String workTitle;
}