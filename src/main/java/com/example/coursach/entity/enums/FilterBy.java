package com.example.coursach.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum FilterBy {
    ALL,
    NOT_STARTED,
    DURING,
    FINISHED,
    DELETED,
    FOR_USER,
    FOR_MENTOR,

}

