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
public enum NotificationType {

    INNER(0), EMAIL(1), SMS(2);
    private static final Map<Integer, NotificationType> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(NotificationType.values())
                .collect(Collectors.toMap(NotificationType::getVal, Function.identity())));
    }

    public static NotificationType lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
