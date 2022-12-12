package com.example.coursach.converters;

import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.CheckReportDto;
import com.example.coursach.dto.ReportCreateDto;
import com.example.coursach.dto.ReportDto;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Report;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ReportConverter {

    public Report toEntity(ReportCreateDto reportCreateDto, User user, Work work, User lector) {

        return Report.builder()
                .answerUrl(reportCreateDto.getAnswerUrl())
                .lector(lector)
                .work(work)
                .user(user)
                .status(reportCreateDto.getReportStatus())
                .build();
    }

    public Report toEntity(CheckReportDto checkReportDto,Report report) {

        report.setComment(checkReportDto.getComments());
        report.setStatus(checkReportDto.getReportStatus());
        report.setRating(checkReportDto.getRating());

        return report;
    }

    public ReportDto toDto(Report report) {
        return ReportDto.builder()
                .reportStatus(report.getStatus())
                .comments(report.getComment())
                .workId(report.getWork().getId())
                .lectorId(report.getLector().getId())
                .userId(report.getUser().getId())
                .rating(report.getRating())
                .answerUrl(report.getAnswerUrl())
                .build();
    }
}
