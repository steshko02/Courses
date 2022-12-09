package com.example.coursach.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum UserStatus {
    NOT_ACTIVE(0), ACTIVE(1);

    private static final Map<Integer, UserStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(UserStatus.values())
                .collect(Collectors.toMap(UserStatus::getVal, Function.identity())));
    }

    public static UserStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }
}
