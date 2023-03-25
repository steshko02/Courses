package com.example.coursach.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ModelNumberHiding {

    private final int hideCount = 4;
    private final String defaultString = "*****";

    public String hide(String number) {
        if (number.length() - hideCount <= 0) {
            return defaultString;
        }

        String hideNumber = number.substring(number.length() - hideCount);
        return String.format("%" + (number.length()) + "s", hideNumber).replace(" ", "*");
    }
}
