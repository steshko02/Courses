package com.example.coursach.entity.message;

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
public enum MessageLocale {

    EN("en"), RU("ru");

    private static final Map<String, MessageLocale> LOOKUP;

    private final String language;

    static {
        LOOKUP = Collections.unmodifiableMap(Arrays.stream(MessageLocale.values())
                .collect(Collectors.toMap(MessageLocale::getLanguage, Function.identity())));
    }

    public static MessageLocale lookup(String language) {
        return lookupOptional(language)
                .orElseThrow(() -> new IllegalArgumentException("Unknown language " + language));
    }

    public static Optional<MessageLocale> lookupOptional(String language) {
        return Optional.ofNullable(LOOKUP.get(language));
    }

}
