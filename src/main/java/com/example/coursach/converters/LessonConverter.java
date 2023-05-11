package com.example.coursach.converters;

import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.LessonShortInfoDto;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LessonConverter {
    private final TimeCheckHelper timeCheckHelper;

    public Lesson toEntity(LessonDto lessonDto, Course course, List<User> mentors) {

        return Lesson.builder()
                .course(course)
                .description(lessonDto.getDescription())
                .title(lessonDto.getTitle())
                .status(timeCheckHelper.checkAndGetTimeStatus(lessonDto.getDateStart(),lessonDto.getDateEnd()))
                .mentors(mentors)
                .order(lessonDto.getOrder())
                .start(Optional.ofNullable(lessonDto.getDateStart().toLocalDateTime()).orElse(null))
                .end(Optional.ofNullable(lessonDto.getDateEnd().toLocalDateTime()).orElse(null))
                .build();
    }

    public LessonDto toDto(Lesson lesson) {
        return LessonDto.builder()
                .courseId(lesson.getCourse().getId())
                .description(lesson.getDescription())
                .title(lesson.getTitle())
                .build();
    }

    public LessonDtoWithMentors toDto(Lesson lesson, List<BaseUserInformationDto> mentors, Optional<WorkDto> workDto) {
        return LessonDtoWithMentors.builder()
                .courseId(lesson.getCourse().getId())
                .description(lesson.getDescription())
                .title(lesson.getTitle())
                .mentors(mentors)
                .status(lesson.getStatus())
                .dateEnd(lesson.getEnd().atZone(ZoneId.systemDefault()))
                .dateStart(lesson.getStart().atZone(ZoneId.systemDefault()))
                .work(workDto.orElse(null))
                .build();
    }

    public LessonShortInfoDto toDtoSortInfo(Lesson lesson){
       return LessonShortInfoDto.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .status(lesson.getStatus())
                .build();
    }
}
