
package com.example.coursach.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Generator {

    public Integer generateSixDigitalCode() {
        int min = 100000;
        int max = 999999;
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

}
