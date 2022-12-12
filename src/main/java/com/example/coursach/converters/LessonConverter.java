package com.example.coursach.converters;

import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Resource;
import com.example.coursach.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class LessonConverter {
    public Lesson toEntity(LessonDto lessonDto, Course course, Resource resource) {

        return Lesson.builder()
                .course(course)
                .status(lessonDto.getStatus())
                .description(lessonDto.getDescription())
                .duration(lessonDto.getDuration())
                .resource(resource)
                .sourceUrl(lessonDto.getSourceUrl())
                .title(lessonDto.getTitle())
                .build();
    }

    public LessonDto toDto(Lesson lesson) {
        return LessonDto.builder()
                .courseId(lesson.getCourse().getId())
                .status(lesson.getStatus())
                .description(lesson.getDescription())
                .duration(lesson.getDuration())
                .resourceId(lesson.getResource().getId())
                .sourceUrl(lesson.getSourceUrl())
                .title(lesson.getTitle())
                .build();
    }
}
