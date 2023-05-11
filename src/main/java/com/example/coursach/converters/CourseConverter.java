package com.example.coursach.converters;


import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.CourseDtoWithMentors;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.LessonShortInfoDto;
import com.example.coursach.dto.ResourceDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.service.MinioStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final MinioStorageService minioStorageService;
    private final LessonConverter lessonConverter;
    private final TimeCheckHelper timeCheckHelper;

    public Course toEntity(CourseDto courseDto) {

        return Course.builder()
                .size(courseDto.getSize())
                .description(courseDto.getDescription())
                .title(courseDto.getTitle())
                .status(courseDto.getStatus())
                .status(timeCheckHelper.checkAndGetTimeStatus(courseDto.getDateStart(),courseDto.getDateEnd()))
                .end(courseDto.getDateEnd().toLocalDateTime())
                .start(courseDto.getDateStart().toLocalDateTime())
                .build();
    }

    public List<CourseDto> toDtos(List<Course> courses) {
        return courses.stream().map(this::toDtoWithLesson).collect(Collectors.toList());
    }

    public CourseDto toDtoWithLesson(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .size(course.getSize())
                .description(course.getDescription())
                .title(course.getTitle())
                .status(course.getStatus())
                .dateEnd(course.getEnd().atZone(ZoneId.systemDefault()))
                .dateStart(course.getStart().atZone(ZoneId.systemDefault()))
                .lessons(course.getLessons().stream().map(lessonConverter::toDtoSortInfo).collect(Collectors.toList()))
                .resource(Optional.ofNullable(course.getResources()).map(x->ResourceDto.builder()
                        .url(x.getUrl())
                        .build()).orElse(null))
                .build();
    }

    public CourseDtoWithMentors toDtoWithLessonAndMentors(Course course, List<BaseUserInformationDto> baseUserInformationDtos) {
        return CourseDtoWithMentors.builder()
                .id(course.getId())
                .size(course.getSize())
                .description(course.getDescription())
                .title(course.getTitle())
                .status(course.getStatus())
                .dateEnd(course.getEnd().atZone(ZoneId.systemDefault()))
                .dateStart(course.getStart().atZone(ZoneId.systemDefault()))
                .lessons(course.getLessons().stream()
                        .sorted(Comparator.comparing(Lesson::getStart, Comparator.nullsLast(Comparator.naturalOrder())))
                        .map(lessonConverter::toDtoSortInfo)
                        .collect(Collectors.toList()))
                .resource(Optional.ofNullable(course.getResources()).map(x->ResourceDto.builder()
                        .url(x.getUrl())
                        .build()).orElse(null))
                .mentors(baseUserInformationDtos)
                .build();
    }
}
