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
public enum ResourceType {

    VIDEO(0),
    PICTURE(1),
    DOCUMENT(2);
    private static final Map<Integer, ResourceType> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(ResourceType.values())
                .collect(Collectors.toMap(ResourceType::getVal, Function.identity())));
    }

    public static ResourceType lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }
}

