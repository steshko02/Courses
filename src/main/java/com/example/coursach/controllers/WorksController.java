package com.example.coursach.controllers;

import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.MinioStorageService;
import com.example.coursach.service.WorkService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("works")
public class WorksController {
    private final WorkService workService;
    private final MinioStorageService minioStorageService;

    @PostMapping
    @ResponseBody
    public Long create(@RequestBody WorkDto workDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        return workService.createWork(workDto,authorizedUser.getUuid());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        workService.deleteWork(id,authorizedUser.getUuid());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateWork(@RequestBody WorkDto workDto,  @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        workService.update(workDto, authorizedUser.getUuid());
    }

    @PostMapping("/upload-files/{courseId}/{lessonId}/{workId}")
    public List<StatusDto> uploadFiles(@Size(max = 5, message = "Exceeded maximum file count") @RequestPart("file") MultipartFile[] files,
                                       @PathVariable Long courseId,
                                       @PathVariable Long lessonId,
                                       @PathVariable Long workId,
                                       @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return Arrays.stream(files).map(file -> minioStorageService.uploadWorkObj(file, courseId, lessonId,workId)).collect(Collectors.toList());
    }


    @PostMapping("/upload/{courseId}/{lessonId}/{workId}")
    public StatusDto uploadFile(@RequestParam("file") MultipartFile picture,
                                @PathVariable Long courseId,
                                @PathVariable Long lessonId,
                                @PathVariable Long workId,
                                @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.uploadWorkObj(picture, courseId, lessonId,workId);
    }

    @GetMapping("/byLesson/{lessId}")
    @ResponseBody
    public WorkDto getById(@PathVariable("lessId") Long lessId,
                                    @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return workService.getBylessId(lessId,authorizedUser.getUuid());
    }
}
