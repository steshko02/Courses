package com.example.coursach.service;

import com.example.coursach.converters.BookingConverter;
import com.example.coursach.converters.CourseConverter;
import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.CourseDto;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Role;
import com.example.coursach.entity.User;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.BookingRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;
    private final CourseConverter courseConverter;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseUserRepository courseUserRepository;

    public CourseDto createBookings(BookingDto bookingDto) {

        Course course = courseRepository.findById(bookingDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        User user = userRepository.findById(bookingDto.getUserId())
                .orElseThrow(RuntimeException::new);

        Booking booking = bookingConverter.toEntity(bookingDto, course, user);
        if(bookingDto.getDateCreation()==null){
            booking.setDateCreation(LocalDateTime.now());
        }
        bookingRepository.save(booking);
        return courseConverter.toDto(course);
    }

    public void deleteBookings(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        User user = userRepository.findById(booking.getUser().getId()).get();
        Course course = courseRepository.findById(booking.getCourse().getId()).get();
        bookingRepository.deleteById(id);
        courseUserRepository.deleteById(UserCourseId.builder().courseId(course.getId()).usersId(user.getId()).build());
    }

    public void approve(Long id) {
        Booking booking = bookingRepository.findById(id).get();
        User user = userRepository.findById(booking.getUser().getId()).get();
        Course course = courseRepository.findById(booking.getCourse().getId()).get();

        CourseUser courseUser = CourseUser.builder()
                .id(UserCourseId.builder().courseId(course.getId()).usersId(user.getId()).build())
                .role(Role.builder().id(4L).name(UserRole.USER).build())
                .build();

        courseUserRepository.save(courseUser);

        user.getCourses().add(course);
        course.getUsers().add(user);

        userRepository.save(user);
        courseRepository.save(course);
        booking.setStatus(BookingStatus.APPROWED);
        bookingRepository.save(booking);
    }
}

