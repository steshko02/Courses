package com.example.coursach.controllers;

import com.example.coursach.dto.PaginationBookingDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("bookings")
public class BookingController {
    private final BookingService bookingService;

//    @PostMapping
//    @ResponseBody
//    public CourseDto bookings(@RequestBody BookingDto bookingDto) {
//       return bookingService.createBookings(bookingDto);
//    }

    @PostMapping("/{courseId}")
//    @ResponseBody
    public Long bookCourse(@PathVariable("courseId") Long id, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
       return bookingService.createBookings(id,authorizedUser.getUuid());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookingService.deleteBookings(id);
    }

    @PostMapping("approve/{id}")
    public void approve(@PathVariable("id") Long id) {
        bookingService.approve(id);
    }

    @PostMapping("canceled/{id}")
    public void cancel(@PathVariable("id") Long id) {
        bookingService.canceled(id);
    }

    @GetMapping("/all")
    @ResponseBody
    public PaginationBookingDto getAll(@RequestParam("number") Integer number,
                                       @RequestParam("size") Integer size) {
        return bookingService.getAllWithPagination(number,size);
    }

}
