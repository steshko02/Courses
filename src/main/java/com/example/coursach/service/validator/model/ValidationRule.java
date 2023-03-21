package com.example.coursach.service.validator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@RequiredArgsConstructor
public class ValidationRule {

    @NotNull
    private final ValidationType type;

    private final String regexp;

    private final String max;

    private final String min;

    @NotNull
    private final String errorMessage;

    @AssertTrue
    public boolean isValid() {
        return type == ValidationType.NOT_NULL
                || type == ValidationType.CURRENCY
                || type == ValidationType.NUMBER
                || !StringUtils.isAllBlank(regexp, max, min);
    }
}
