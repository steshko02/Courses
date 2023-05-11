package com.example.coursach.converters;

import com.example.coursach.dto.ResourceDto;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Work;
import com.example.coursach.entity.enums.TimeStatus;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Component
public class WorkConverter {
    public Work toEntity(WorkDto workDto) {
        ZonedDateTime now = ZonedDateTime.now();
        return Work.builder()
                .status( workDto.getDeadline().isBefore(now) ? TimeStatus.FINISHED: TimeStatus.DURING)
                .description(workDto.getDescription())
                .title(workDto.getTitle())
                .deadline(workDto.getDeadline().toLocalDateTime())
                .build();
    }

    public WorkDto toDto(Work work) {
        return WorkDto.builder()
                .id(work.getId())
                .status(work.getStatus())
                .description(work.getDescription())
                .deadline(work.getDeadline().atZone(ZoneId.systemDefault()))
                .title(work.getTitle())
                .resource(work.getResources().stream().map(r-> ResourceDto.builder()
                        .filename(r.getFilename())
                        .extension(r.getExtension())
                        .url(r.getUrl())
                        .id(r.getId())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
