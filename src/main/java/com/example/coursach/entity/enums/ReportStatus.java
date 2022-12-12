package com.example.coursach.entity.enums;

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
public enum ReportStatus {

    IN_TIME(0), OVERDUE(1);
    private static final Map<Integer, ReportStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(ReportStatus.values())
                .collect(Collectors.toMap(ReportStatus::getVal, Function.identity())));
    }

    public static ReportStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}