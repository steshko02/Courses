package com.example.coursach.controllers;

import com.example.coursach.dto.LessonDto;
import com.example.coursach.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("lessons")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createLesson(@RequestBody LessonDto lessonDto) {
        lessonService.createLesson(lessonDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public LessonDto get(@PathVariable("id") Long id) {
        return lessonService.getById(id);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateLesson(@RequestBody LessonDto lessonDto) {
        lessonService.update(lessonDto);
    }
}
