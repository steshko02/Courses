package com.example.coursach.dto.pagable;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Getter
public class PageableRequestDto {

    public static final int MIN_NUMBER = 1;
    public static final int MIN_SIZE = 5;

    @Min(value = MIN_NUMBER)
    private final Integer number;

    @Min(value = MIN_SIZE)
    @Max(value = 20)
    private final Integer size;

    @Builder
    @JsonCreator
    public PageableRequestDto(Integer number, Integer size) {
        this.number = Optional.ofNullable(number).orElse(MIN_NUMBER);
        this.size = Optional.ofNullable(size).orElse(MIN_SIZE);
    }

}
