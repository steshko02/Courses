package com.example.coursach.entity.converters;

import com.example.coursach.entity.CourseStatus;

import javax.persistence.AttributeConverter;

public class CourseStatusDeletedConverter implements AttributeConverter<CourseStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CourseStatus notificationType) {
        return notificationType.getVal();
    }

    @Override
    public CourseStatus convertToEntityAttribute(Integer integer) {
        return CourseStatus.lookup(integer);
    }
}

