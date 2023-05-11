package com.example.coursach.controllers;

import com.example.coursach.dto.AnswerDto;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.AnswerService;
import com.example.coursach.service.MinioStorageService;
import com.example.coursach.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("answer")
public class AnswerController {
    private final AnswerService answerService;
    private final MinioStorageService minioStorageService;

    @PostMapping
    @ResponseBody
    public Long create(@RequestBody AnswerDto answerDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return answerService.createAnswer(answerDto, authorizedUser.getUuid());
    }

//    @DeleteMapping("/{id}")
//    public void delete(@PathVariable("id") Long id) {
//        workService.deleteWork(id);
//    }
//
//    @PutMapping
//    @ResponseStatus(code = HttpStatus.NO_CONTENT)
//    public void updateWork(@RequestBody WorkDto workDto) {
//        workService.update(workDto);
//    }

    @PostMapping("/upload/{answerId}")
    public StatusDto uploadFile(@RequestParam("file") MultipartFile picture,
                                @PathVariable Long answerId,
                                @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.uploadAnswerObj(picture,authorizedUser.getUuid(),answerId);
    }
}
