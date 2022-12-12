package com.example.coursach.converters;

import com.example.coursach.dto.BookingDto;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.User;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class BookingConverter {
    public Booking toEntity(BookingDto bookingDto, Course course, User user) {

        return Booking.builder()
                .course(course)
                .user(user)
                .status(bookingDto.getStatus())
                .build();
    }

    public BookingDto toDto(Booking booking) {
        return BookingDto.builder()
                .courseId(booking.getCourse().getId())
                .userId(booking.getUser().getId())
                .status(booking.getStatus())
                .dateCreation(booking.getDateCreation().atZone(ZoneId.systemDefault()))
                .build();
    }
}
