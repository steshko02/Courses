package com.example.coursach.controllers;

import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.LessonService;
import com.example.coursach.service.MinioStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("lessons")
public class LessonController {

    private final LessonService lessonService;
    private final MinioStorageService minioStorageService;

    @PostMapping
    public Long createLesson(@RequestBody LessonDto lessonDto) {
        return lessonService.createLesson(lessonDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public LessonDtoWithMentors get(@PathVariable("id") Long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return lessonService.getById(id,authorizedUser.getUuid());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateLesson(@RequestBody LessonDto lessonDto) {
        lessonService.update(lessonDto);
    }

    @PostMapping("/upload/{courseId}/{lessonId}")
    public StatusDto uploadFile(@RequestParam("file") MultipartFile picture, @PathVariable Long courseId, @PathVariable Long lessonId, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.uploadLessonObj(picture, courseId, lessonId);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public Long delete(@PathVariable("id") Long id) {
        return lessonService.deleteById(id);
    }

}
