package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.TimeStatus;
import jakarta.persistence.AttributeConverter;

public class CourseStatusConverter implements AttributeConverter<TimeStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TimeStatus notificationType) {
        return notificationType.getVal();
    }

    @Override
    public TimeStatus convertToEntityAttribute(Integer integer) {
        return TimeStatus.lookup(integer);
    }
}
