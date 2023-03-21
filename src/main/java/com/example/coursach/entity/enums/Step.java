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
public enum Step {

    DETAILS(0), FINANCES(1), RULES(2);

    private static final Map<Integer, Step> LOOKUP;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(Step.values())
                .collect(Collectors.toMap(Step::getVal, Function.identity())));
    }

    private final int val;

    public static Step lookup(int val) {
        return Optional.ofNullable(LOOKUP.get(val)).orElseThrow(() ->
                new IllegalArgumentException("Unknown value " + val));
    }

    public static List<String> getStepTypes() {
        return LOOKUP.values()
                .stream()
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

}