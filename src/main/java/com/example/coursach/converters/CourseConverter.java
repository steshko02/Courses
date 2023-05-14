package com.example.coursach.converters;


import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.CourseDtoForMentors;
import com.example.coursach.dto.CourseDtoWithMentors;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.LessonShortInfoDto;
import com.example.coursach.dto.ResourceDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.service.MinioStorageService;
import com.example.coursach.service.converter.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CourseConverter {

    private final MinioStorageService minioStorageService;
    private final LessonConverter lessonConverter;
    private final TimeCheckHelper timeCheckHelper;
    private final UserConverter userConverter;

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

    public List<CourseDtoForMentors> toDtosForMentors(List<Course> courses, Map<Long, List<User>> collect) {
        return courses.stream().map(c-> toDtoWithMentors(c, collect.get(c.getId()))).collect(Collectors.toList());
    }

    public CourseDtoForMentors toDtoWithMentors(Course course, List<User> users) {
        return CourseDtoForMentors.builder()
                .id(course.getId())
                .title(course.getTitle())
                .status(course.getStatus())
                .dateEnd(course.getEnd().atZone(ZoneId.systemDefault()))
                .dateStart(course.getStart().atZone(ZoneId.systemDefault()))
                .lessons(course.getLessons().stream()
                        .map(lessonConverter::toDtoWithMentors).collect(Collectors.toList()))
                .resource(Optional.ofNullable(course.getResources()).map(x->ResourceDto.builder()
                        .url(x.getUrl())
                        .build()).orElse(null))
                .mentors(userConverter.listUserToListBaseUserInformationDto(users))
                .build();
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

    public CourseDtoWithMentors toDtoWithLessonAndMentors(Course course,List<Lesson> lessons, List<BaseUserInformationDto> baseUserInformationDtos) {
        return CourseDtoWithMentors.builder()
                .id(course.getId())
                .size(course.getSize())
                .description(course.getDescription())
                .title(course.getTitle())
                .status(course.getStatus())
                .busy(course.getCount())
                .dateEnd(course.getEnd().atZone(ZoneId.systemDefault()))
                .dateStart(course.getStart().atZone(ZoneId.systemDefault()))
                .lessons(lessons.stream()
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
