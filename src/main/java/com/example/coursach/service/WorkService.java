package com.example.coursach.service;

import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Work;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final LessonRepository lessonRepository;
    private final WorkConverter workConverter;
    private final WorkRepository workRepository;

    public Long createWork(WorkDto workDto) {

        Lesson lesson = lessonRepository.findById(workDto.getLessonId())
                .orElseThrow(RuntimeException::new);
        Work newEntity = workConverter.toEntity(workDto);
        if(lesson.getWork()==null){
             workRepository.save(newEntity);
            newEntity.setLesson(lesson);
            lesson.setWork(newEntity);
            lessonRepository.save(lesson);
            return newEntity.getId();
        }else {
            Work work = lesson.getWork();
            work.setDeadline(newEntity.getDeadline());
            work.setDescription(newEntity.getDescription());
            work.setStatus(newEntity.getStatus());
            work.setTitle(newEntity.getTitle());
            work.setResources(new ArrayList<>());
            workRepository.save(work);
            return workRepository.save(work).getId();
        }
    }

    public void deleteWork(Long id) {
        workRepository.deleteById(id);
    }

    public void update(WorkDto workDto) {
        Lesson lesson = lessonRepository.findById(workDto.getLessonId())
                .orElseThrow(RuntimeException::new);

        Work work = workConverter.toEntity(workDto);
        work.setId(workDto.getId());

        workRepository.save(work);
    }

    public void checkAndSwitchStatus(){
        LocalDateTime now = LocalDateTime.now();
        workRepository.updateWorkByTime(now);
    }

    public WorkDto getBylessId(Long lessId, String uuid) {

        Work work = lessonRepository.findById(lessId).map(Lesson::getWork)
                .orElseThrow(RuntimeException::new);

        return Optional.ofNullable(work).map(workConverter::toDto).orElse(WorkDto.builder().build());
    }
}
