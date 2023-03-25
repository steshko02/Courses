package com.example.coursach.service.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@UtilityClass
public class PeriodTimeCreator {

    public Map<String, Long> givenDatePeriod(LocalDateTime fromDateTime, LocalDateTime toDateTime) {

        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);

        return Map.of("days", days, "hours", hours, "minutes", minutes);
    }

}
