package com.example.coursach.controllers;

import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
public class CoursesController {
    private final CourseService courseService;

    @PostMapping
    public Long createCourse(@RequestBody CourseDto courseDto) {
       return courseService.createCourse(courseDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public CourseDto get(@PathVariable("id") Long id) {
        return courseService.getById(id);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void update(@RequestBody CourseDto courseDto) {
        courseService.update(courseDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
         courseService.delete(id);
    }
}
