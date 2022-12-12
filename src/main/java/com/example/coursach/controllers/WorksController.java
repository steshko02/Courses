package com.example.coursach.controllers;

import com.example.coursach.dto.WorkDto;
import com.example.coursach.service.WorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("works")
public class WorksController {
    private final WorkService workService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void bookings(@RequestBody WorkDto workDto) {
        workService.createWork(workDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        workService.deleteWork(id);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateWork(@RequestBody WorkDto workDto) {
        workService.update(workDto);
    }
}
