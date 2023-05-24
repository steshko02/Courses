package com.example.coursach.controllers;

import com.amazonaws.services.s3.AmazonS3;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.file.FileUploadMetaRequestDto;
import com.example.coursach.service.LessonService;
import com.example.coursach.service.file.FileLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/presigned-aws-url/{courseId}/{lessonId}/{ext}")
    @ResponseBody
    public String getPresignedUrl(@PathVariable Long courseId,
                                  @PathVariable Long lessonId,
                                  @PathVariable String ext) {
       return uploadService.createUploadUrl(FileUploadMetaRequestDto.builder()
                       .courseId(courseId)
                       .extension(ext)
                       .lessonId(lessonId)
               .build());
    }
}
