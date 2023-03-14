package com.example.coursach.service.model;

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
public enum LocalMessageCodes {

    NEW_PASSWORD_SUCCESS("new.password.success"),
    NEW_PASSWORD_SUCCESS_REQUESTED("new.password.success.requested");

    private static final Map<String, LocalMessageCodes> LOOKUP;

    private final String code;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(LocalMessageCodes.values())
                .collect(Collectors.toMap(LocalMessageCodes::getCode, Function.identity())));
    }

    public static LocalMessageCodes lookup(String code) {
        return Optional.ofNullable(LOOKUP.get(code))
                .orElseThrow(() -> new IllegalArgumentException("Unknown code " + code));
    }

}
