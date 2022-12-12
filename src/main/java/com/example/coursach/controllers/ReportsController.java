package com.example.coursach.controllers;

import com.example.coursach.dto.CheckReportDto;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.ReportCreateDto;
import com.example.coursach.service.ReportService;
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
@RequestMapping("reports")
public class ReportsController {
    private final ReportService reportsService;

    @PostMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void report(@RequestBody ReportCreateDto reportsDto) {
        reportsService.create(reportsDto);
    }

    @PutMapping("/forUser")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateForUser(@RequestBody ReportCreateDto reportCreateDto) {
        reportsService.updateForUser(reportCreateDto);
    }

    @PutMapping("/forLector")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateForLector(@RequestBody CheckReportDto checkReportDto) {
        reportsService.updateForLector(checkReportDto);
    }
}
