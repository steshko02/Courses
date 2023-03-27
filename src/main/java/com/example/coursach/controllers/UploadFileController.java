package com.example.coursach.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.file.FileUploadMetaRequestDto;
import com.example.coursach.service.LessonService;
import com.example.coursach.service.file.FileLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/upload")
public class UploadFileController {

    private final LessonService lessonService;
    private final FileLoaderService uploadService;
    private final AmazonS3 s3Client;

    @PostMapping
    public Long createLesson(@RequestBody LessonDto lessonDto) {
      return   lessonService.createLesson(lessonDto);
    }

    @GetMapping("/presigned-aws-url")
    @ResponseBody
    public String getPresignedUrl(@RequestBody FileUploadMetaRequestDto requestDto) {
       return uploadService.createUploadUrl(requestDto);
    }
}
