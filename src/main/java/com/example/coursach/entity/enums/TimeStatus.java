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
public enum TimeStatus {
    NOT_STARTED(0), DURING(1), FINISHED(2);
    private static final Map<Integer, TimeStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(TimeStatus.values())
                .collect(Collectors.toMap(TimeStatus::getVal, Function.identity())));
    }

    public static TimeStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
