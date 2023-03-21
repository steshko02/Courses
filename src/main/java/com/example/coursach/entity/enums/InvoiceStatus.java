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
public enum InvoiceStatus {

    DELETED(0), ACTIVE(1);
    private static final Map<Integer, InvoiceStatus> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(InvoiceStatus.values())
                .collect(Collectors.toMap(InvoiceStatus::getVal, Function.identity())));
    }

    private final int val;

    public static InvoiceStatus lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val)).orElseThrow(() ->
                new IllegalArgumentException("Unknown value " + val));
    }

}
