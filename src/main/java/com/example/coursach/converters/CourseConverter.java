package com.example.coursach.converters;


import com.example.coursach.dto.CourseDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {
    public Course toEntity(CourseDto courseDto) {

        return Course.builder()
                .size(courseDto.getSize())
                .description(courseDto.getDescription())
                .title(courseDto.getTitle())
                .status(courseDto.getStatus())
                .end(courseDto.getEnd().toLocalDateTime())
                .start(courseDto.getStart().toLocalDateTime())
                .type(courseDto.getType())
                .build();
    }

    public CourseDto toDto(Course course) {
        return CourseDto.builder()
                .size(course.getSize())
                .description(course.getDescription())
                .title(course.getTitle())
                .status(course.getStatus())
                .end(course.getEnd().atZone(ZoneId.systemDefault()))
                .start(course.getStart().atZone(ZoneId.systemDefault()))
                .type(course.getType())
                .ids(course.getLessons().stream().map(Lesson::getId).collect(Collectors.toList()))
                .build();
    }
}
