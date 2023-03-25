package com.example.coursach.dto.notification;

import com.example.coursach.dto.pagable.PageableRequestDto;
import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import java.util.Optional;

@Getter
public class NotificationRequestParamsDto {

    @Valid
    private final PageableRequestDto page;

    @Builder
    public NotificationRequestParamsDto(Integer number, Integer size) {
        this.page = PageableRequestDto
                .builder()
                .number(Optional.ofNullable(number).orElse(PageableRequestDto.MIN_NUMBER))
                .size(Optional.ofNullable(size).orElse(PageableRequestDto.MIN_SIZE))
                .build();
    }

}
