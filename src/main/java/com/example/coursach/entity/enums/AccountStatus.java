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
public enum AccountStatus {

    INVITED(0), ACTIVE(1);

    private static final Map<Integer, AccountStatus> LOOKUP;

    private final int val;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(AccountStatus.values())
                .collect(Collectors.toMap(AccountStatus::getVal, Function.identity())));
    }

    public static AccountStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val))
                .orElseThrow(() -> new IllegalArgumentException("Unknown value " + val));
    }

}
