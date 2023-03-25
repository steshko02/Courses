package com.example.coursach.service.utils;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class RequestDatesWrapper {

    private final LocalDateTime utcStartDate;

    private final LocalDateTime utcEndDate;

}