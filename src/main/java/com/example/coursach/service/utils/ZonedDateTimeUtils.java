package com.example.coursach.service.utils;

import com.example.coursach.exception.bookings.InvalidDateIntervalException;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class ZonedDateTimeUtils {

    public static final String UTC = "UTC";

    public RequestDatesWrapper prepareRequestDates(
            ZonedDateTime requestStartDate, ZonedDateTime requestEndDate, Clock systemClock, long daysDuration) {

        if (Objects.isNull(requestStartDate) && Objects.isNull(requestEndDate)) {
            ZonedDateTime endDate = ZonedDateTime.now(systemClock);
            return buildRequestDates(endDate.minusDays(daysDuration), endDate);
        }

        ZonedDateTime startDate = Optional.ofNullable(requestStartDate).orElseGet(() -> requestEndDate.minusDays(daysDuration));
        ZonedDateTime endDate = Optional.ofNullable(requestEndDate).orElseGet(() -> requestStartDate.plusDays(daysDuration));

        validateByIntervalMaxDuration(startDate, endDate, daysDuration);

        return buildRequestDates(startDate, endDate);
    }

    public RequestDatesWrapper buildRequestDates(ZonedDateTime start, ZonedDateTime end) {
        return RequestDatesWrapper.builder()
                .utcStartDate(switchToUtc(start).toLocalDateTime())
                .utcEndDate(switchToUtc(end).toLocalDateTime())
                .build();
    }

    public ZonedDateTime switchToUtc(ZonedDateTime zonedDateTime) {
        return zonedDateTime.withZoneSameInstant(ZoneId.of(UTC));
    }

    public void validateByIntervalMaxDuration(ZonedDateTime startDate, ZonedDateTime endDate, long daysDuration) {
        if (startDate.plusDays(daysDuration).isBefore(endDate) || startDate.isAfter(endDate)) {
            throw new InvalidDateIntervalException();
        }
    }

    public ZonedDateTime addUtcZone(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, ZoneId.of(ZonedDateTimeUtils.UTC));
    }

}
