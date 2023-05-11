package com.example.coursach.service;

import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Work;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final LessonRepository lessonRepository;
    private final WorkConverter workConverter;
    private final WorkRepository workRepository;

    public Long createWork(WorkDto workDto) {

        Lesson lesson = lessonRepository.findById(workDto.getLessonId())
                .orElseThrow(RuntimeException::new);

        //todo sheets
        Work save = workRepository.save(workConverter.toEntity(workDto));
        save.setLesson(lesson);
        lesson.setWork(save);
        lessonRepository.save(lesson);
        return save.getId();
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
}
