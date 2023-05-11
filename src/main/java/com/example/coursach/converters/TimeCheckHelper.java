package com.example.coursach.converters;

import com.example.coursach.entity.enums.TimeStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Service
public class TimeCheckHelper {

    public TimeStatus checkAndGetTimeStatus(ZonedDateTime start, ZonedDateTime end) {

        ZonedDateTime now = ZonedDateTime.now();

        if(now.isAfter(start) && now.isBefore(end)){
            return TimeStatus.DURING;
        }
        if(now.isAfter(end)) {
            return TimeStatus.FINISHED;
        }
        return TimeStatus.NOT_STARTED;

    }
}
