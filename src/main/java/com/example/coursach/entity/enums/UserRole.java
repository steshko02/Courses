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
public enum UserRole {

    USER(0), ADMIN(1), STUDENT(2), LECTURER(3);
    private static final Map<Integer, UserRole> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(UserRole.values())
                .collect(Collectors.toMap(UserRole::getVal, Function.identity())));
    }

    public static UserRole lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
