package com.example.coursach.service;

import com.example.coursach.converters.LessonConverter;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Resource;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonConverter lessonConverter;
    private final CourseRepository courseRepository;
    private final ResourceRepository repository;

    public void createLesson(LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        Resource resource = repository.findById(lessonDto.getResourceId())
                .orElse(null);

        lessonRepository.save(lessonConverter.toEntity(lessonDto, course, resource));
    }

    public LessonDto getById(Long id) {
        return lessonConverter.toDto(lessonRepository.findById(id).get());
    }


    public void update(LessonDto lessonDto) {

        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        Resource resource = repository.findById(lessonDto.getResourceId())
                .orElse(null);

        Lesson newLesson = lessonConverter.toEntity(lessonDto, course, resource);
        newLesson.setId(lessonDto.getId());

        lessonRepository.save(newLesson);
    }
}
