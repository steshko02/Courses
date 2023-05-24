package com.example.coursach.controllers;

import com.example.coursach.dto.CheckWorkDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.CheckWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("check_work")
public class СheckWorkController {

    private final CheckWorkService checkWorkService;

    @PostMapping
    @ResponseBody
    public Long create(@RequestBody CheckWorkDto checkWorkDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) throws AccessDeniedException {
        return checkWorkService.createAnswer(checkWorkDto, authorizedUser.getUuid());
    }
}
