package com.example.coursach.service;

import com.example.coursach.converters.CourseConverter;
import com.example.coursach.converters.EventConverter;
import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.EventDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Profile;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseConverter courseConverter;
    private final CourseRepository courseRepository;

    public void createCourse(CourseDto courseDto) {
        courseRepository.save(courseConverter.toEntity(courseDto));
    }

    public CourseDto getById(Long id) {
        return courseConverter.toDto(courseRepository.findById(id).get());
    }

    public void update(CourseDto courseDto) {
        Course newCourse = courseConverter.toEntity(courseDto);
        newCourse.setId(courseDto.getId());
        courseRepository.save(newCourse);
    }

    public void delete(Long id) {
       courseRepository.deleteById(id);
    }
}
