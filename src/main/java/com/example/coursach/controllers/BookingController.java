package com.example.coursach.controllers;

import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.CourseDto;
import com.example.coursach.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("booking")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    @ResponseBody
    public CourseDto bookings(@RequestBody BookingDto bookingDto) {
       return bookingService.createBookings(bookingDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookingService.deleteBookings(id);
    }
}
