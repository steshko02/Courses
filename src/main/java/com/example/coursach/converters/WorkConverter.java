package com.example.coursach.converters;

import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Work;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class WorkConverter {
    public Work toEntity(WorkDto workDto, Lesson lesson) {

        return Work.builder()
                .lesson(lesson)
                .status(workDto.getStatus())
                .description(workDto.getDescription())
                .sourceUrl(workDto.getTaskUrl())
                .title(workDto.getTitle())
                .deadline(workDto.getDeadline().toLocalDateTime())
                .build();
    }

    public WorkDto toDto(Work work) {
        return WorkDto.builder()
                .lessonId(work.getLesson().getId())
                .status(work.getStatus())
                .description(work.getDescription())
                .taskUrl(work.getSourceUrl())
                .deadline(work.getDeadline().atZone(ZoneId.systemDefault()))
                .title(work.getTitle())
                .build();
    }
}
