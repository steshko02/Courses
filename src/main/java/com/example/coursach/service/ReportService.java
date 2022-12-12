package com.example.coursach.service;

import com.example.coursach.converters.ReportConverter;
import com.example.coursach.dto.CheckReportDto;
import com.example.coursach.dto.ReportCreateDto;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Report;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.ReportRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final WorkRepository workRepository;
    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final ReportConverter reportConverter;

    public void create(ReportCreateDto reportCreateDto) {

        User user = userRepository.findById(reportCreateDto.getUserId())
                .orElseThrow(RuntimeException::new);

        User lector = userRepository.findById(reportCreateDto.getLectorId())
                .orElseThrow(RuntimeException::new);

        Work work = workRepository.findById(reportCreateDto.getWorkId())
                .orElseThrow(RuntimeException::new);


        reportRepository.save(reportConverter.toEntity(reportCreateDto,user,work,lector));
    }

    public void delete(Long id) {
        reportRepository.deleteById(id);
    }

    public void updateForLector(CheckReportDto checkReportDto) {

        Report report = reportConverter.toEntity(checkReportDto);
        report.setId(checkReportDto.getId());
        reportRepository.save(report);
    }

    public void updateForUser(ReportCreateDto reportCreateDto) {

        User user = userRepository.findById(reportCreateDto.getUserId())
                .orElseThrow(RuntimeException::new);

        User lector = userRepository.findById(reportCreateDto.getLectorId())
                .orElseThrow(RuntimeException::new);

        Work work = workRepository.findById(reportCreateDto.getWorkId())
                .orElseThrow(RuntimeException::new);

        Report report = reportConverter.toEntity(reportCreateDto,user,work,lector);
        report.setId(reportCreateDto.getId());
        reportRepository.save(report);
    }
}
