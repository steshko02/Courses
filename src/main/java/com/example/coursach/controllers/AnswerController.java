package com.example.coursach.controllers;

import com.example.coursach.dto.AnswerDto;
import com.example.coursach.dto.PaginationAnswerDto;
import com.example.coursach.dto.PaginationBookingDto;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.AnswerService;
import com.example.coursach.service.MinioStorageService;
import com.example.coursach.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.AccessDeniedException;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("answer")
public class AnswerController {
    private final AnswerService answerService;
    private final MinioStorageService minioStorageService;

    @PostMapping
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public Long create(@RequestBody AnswerDto answerDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        return answerService.createAnswer(answerDto, authorizedUser.getUuid());
    }

    @PutMapping
    @ResponseBody
    @PreAuthorize("hasRole('USER')")
    public Long update(@RequestBody AnswerDto answerDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        return answerService.update(answerDto, authorizedUser.getUuid());
    }

    @PostMapping("/upload-files/{answerId}")
    @PreAuthorize("hasRole('USER')")
    public List<StatusDto> uploadFiles( @Size(max = 5, message = "Exceeded maximum file count") @RequestPart("file") MultipartFile[] files,
                                                       @PathVariable Long answerId,
                                                       @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return Arrays.stream(files).map(file -> minioStorageService.uploadAnswerObj(file, authorizedUser.getUuid(), answerId)).collect(Collectors.toList());
    }

    @PostMapping("/upload/{answerId}")
    @PreAuthorize("hasRole('USER')")
    public StatusDto uploadFile(@RequestParam("file") MultipartFile picture,
                                @PathVariable Long answerId,
                                @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.uploadAnswerObj(picture, authorizedUser.getUuid(), answerId);
    }

    @GetMapping("byLesson/{id}")
    @ResponseBody
    public PaginationAnswerDto getAll(@RequestParam("number") Integer number,
                                      @RequestParam("size") Integer size,
                                      @PathVariable("id") Long id,
                                      @RequestParam("user") String user,
                                      @RequestParam("status") String status,
                                      @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        return answerService.getByLesson(number,size,id, user, status,authorizedUser.getUuid());
    }
}
