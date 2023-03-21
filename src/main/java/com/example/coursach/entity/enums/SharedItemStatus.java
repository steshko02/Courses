package com.example.coursach.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum SharedItemStatus {
    DRAFT(0), ACTIVE(1);

    private static final Map<Integer, SharedItemStatus> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(SharedItemStatus.values())
                .collect(Collectors.toMap(SharedItemStatus::getVal, Function.identity())));
    }

    private final int val;

    public static SharedItemStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val)).orElseThrow(() ->
                new IllegalArgumentException("Unknown value " + val));
    }

    public static List<String> getStatusTypes() {
        return LOOKUP.values()
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
