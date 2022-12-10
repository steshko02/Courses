package com.example.coursach.service;

import com.example.coursach.converters.BookingConverter;
import com.example.coursach.converters.CourseConverter;
import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.CourseDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.User;
import com.example.coursach.repository.BookingRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;
    private final CourseConverter courseConverter;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseDto createBookings(BookingDto bookingDto) {

        Course course = courseRepository.findById(bookingDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(RuntimeException::new);

        bookingRepository.save(bookingConverter.toEntity(bookingDto,course,user));
        return courseConverter.toDto(course);
    }

    public void deleteBookings(Long id) {
        bookingRepository.deleteById(id);
    }
}
