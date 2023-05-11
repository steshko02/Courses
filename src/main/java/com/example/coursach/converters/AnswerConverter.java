package com.example.coursach.converters;

import com.example.coursach.dto.AnswerDto;
import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.ResourceDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.stream.Collectors;

@Component
public class AnswerConverter {
//    public Answer toEntity(BookingDto bookingDto, Course course, User user) {
//
//        return Booking.builder()
//                .course(course)
//                .user(user)
//                .status(bookingDto.getStatus())
//                .build();
//    }

    public AnswerDto toDto(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .comment(answer.getComment())
                .timeStatus(answer.getStatus())
                .date(answer.getDateCreation())
                .resource(answer.getResources().stream().map(r-> ResourceDto.builder()
                        .filename(r.getFilename())
                        .extension(r.getExtension())
                        .url(r.getUrl())
                        .id(r.getId())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
