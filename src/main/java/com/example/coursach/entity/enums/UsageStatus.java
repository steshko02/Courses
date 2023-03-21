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
public enum UsageStatus {

    CANCELED(0), ACTIVE(1), DELETED(2);

    private static final Map<Integer, UsageStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(UsageStatus.values())
                .collect(Collectors.toMap(UsageStatus::getVal, Function.identity())));
    }

    public static UsageStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
