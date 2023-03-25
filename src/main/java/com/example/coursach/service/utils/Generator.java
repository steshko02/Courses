package com.example.coursach.service.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Generator {

    public Integer generateSixDigitalCode() {
        int min = 100000;
        int max = 999999;
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    public Integer generateBetween(int min, int maxP) {
        int max = maxP;
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}
