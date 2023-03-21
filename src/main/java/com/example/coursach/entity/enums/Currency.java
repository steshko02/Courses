package com.example.coursach.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Currency {

    USD(0), EUR(1), BYN(2);

    private static final Map<Integer, Currency> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(Currency.values())
                .collect(Collectors.toMap(Currency::getVal, Function.identity())));
    }

    private final int val;

    public static Currency lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val)).orElseThrow(() ->
                new IllegalArgumentException("Unknown value " + val));
    }

    public static List<String> getCurrencyTypes() {
        return LOOKUP.values()
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

}