package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.CourseType;
import javax.persistence.AttributeConverter;

public class CourseTypeConverter implements AttributeConverter<CourseType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CourseType courseStatus) {
        return courseStatus.getVal();
    }

    @Override
    public CourseType convertToEntityAttribute(Integer integer) {
        return CourseType.lookup(integer);
    }
}
