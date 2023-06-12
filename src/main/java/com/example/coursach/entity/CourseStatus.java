package com.example.coursach.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CourseStatus {
    NOT_DELETED(0), DELETED(1);
    private static final Map<Integer, CourseStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(CourseStatus.values())
                .collect(Collectors.toMap(CourseStatus::getVal, Function.identity())));
    }

    public static CourseStatus byString(String value){
        try {
            return   CourseStatus.valueOf(value);
        }catch (IllegalArgumentException e){
            return null;
        }
    }
    public static CourseStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
