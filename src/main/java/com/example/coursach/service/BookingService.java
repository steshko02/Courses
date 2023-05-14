package com.example.coursach.service;

import com.example.coursach.converters.BookingConverter;
import com.example.coursach.converters.CourseConverter;
import com.example.coursach.dto.BookingDto;
import com.example.coursach.dto.CourseShortInfoDto;
import com.example.coursach.dto.PaginationBookingDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.User;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.BookingRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.RoleRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.filter.BookingSpecification;
import com.example.coursach.service.converter.UserConverter;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.text.DateFormat;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingConverter bookingConverter;
    private final CourseConverter courseConverter;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseUserRepository courseUserRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    private final EmailSenderService postman;
    private final CurrentUserRequestLocaleService currentUserRequestLocaleService;

    public Long createBookings(Long courseId, String userId) {

        return bookingRepository.findByUser_IdAndCourseId(userId, courseId).map(
                Booking::getId
        ).orElseGet(() -> {
                    Course course = courseRepository.findById(courseId)
                            .orElseThrow(RuntimeException::new);

                    User user = userRepository.findById(userId)
                            .orElseThrow(RuntimeException::new);

                    Booking booking = Booking.builder()
                            .dateCreation(LocalDateTime.now())
                            .status(BookingStatus.CONSIDERED)
                            .user(user)
                            .course(course)
                            .build();
                    return bookingRepository.save(booking).getId();
                }
        );
    }


    public void deleteBookings(Long id) {
//        Booking booking = bookingRepository.findById(id).get();
//        User user = userRepository.findById(booking.getUser().getId()).get();
//        Course course = courseRepository.findById(booking.getCourse().getId()).get();
//        bookingRepository.deleteById(id);
//        courseUserRepository.deleteById(UserCourseId.builder().courseId(course.getId()).usersId(user.getId()).build());
    }

    public void approve(Long id) {
        Booking booking = bookingRepository.findById(id).orElseThrow(RuntimeException::new);
        if (booking.getStatus() == BookingStatus.APPROWED) {
            throw new RuntimeException("User already exists");
        }
        User user = booking.getUser();
        Course course = booking.getCourse();

        CourseUser courseUser = CourseUser.builder()
                .id(UserCourseId.builder().courseId(course.getId()).userId(user.getId()).build())
                .role(roleRepository.findByName(UserRole.STUDENT))
                .build();

        courseUserRepository.save(courseUser);
        booking.setStatus(BookingStatus.APPROWED);
        bookingRepository.save(booking);
        Integer count = course.getCount();
        course.setCount(count == null ? 1 : count + 1);
        courseRepository.save(course);

        String language = currentUserRequestLocaleService.getCurrentLocale().getLanguage();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm");
        String dateString=simpleDateFormat.format(java.sql.Timestamp.valueOf(course.getStart()));
        postman.send(
                Notification.buildBookingNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.BOOKING,
                        user.getEmail(),
                        course.getTitle(),
                        user.getFirstname()+" "+user.getLastname(),
                        "одобрена",
                        "Данный курс стартует " + dateString)
        );
    }

    public PaginationBookingDto getAllWithPagination(Integer number, Integer size, BookingStatus status, String user, String courseStr) {

        Page<Booking> coursePage = null;

        coursePage = bookingRepository
                .findAll(BookingSpecification.createBookingSpecification(courseStr,user,status),
                        PageRequest.of(number, size));


//         coursePage = bookingRepository.findAllByStatus(status,PageRequest.of(number, size));
        List<BookingDto> collect = coursePage.get().map(book -> {
                    BaseUserInformationDto baseUserInformationDto = userConverter.userToBaseUserInformationDto(book.getUser());
                    Course course = book.getCourse();
                    CourseShortInfoDto build = CourseShortInfoDto.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .size(course.getSize())
                            .status(course.getStatus())
                            .count(course.getCount() == null ? 0 : course.getCount())
                            .build();

                    return BookingDto.builder()
                            .id(book.getId())
                            .user(baseUserInformationDto)
                            .dateCreation(book.getDateCreation().atZone(ZoneId.systemDefault()))
                            .status(book.getStatus())
                            .course(build)
                            .build();
                }).sorted(Comparator.comparing(BookingDto::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return PaginationBookingDto.builder()
                .bookings(collect)
                .totalCount(coursePage.getTotalElements())
                .currentPage(number + 1)
                .totalPages(coursePage.getTotalPages())
                .build();
    }

    public void canceled(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        if (booking.getStatus() == BookingStatus.APPROWED) {
            Optional<CourseUser> byId = courseUserRepository.findById(UserCourseId.builder()
                    .courseId(booking.getCourse().getId())
                    .userId(booking.getUser().getId()).build());
            courseUserRepository.delete(byId.get());
            Course course = booking.getCourse();
            course.setCount(course.getCount() - 1);
            courseRepository.save(course);
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        postman.send(
                Notification.buildBookingNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.BOOKING,
                        booking.getUser().getEmail(),
                        booking.getCourse().getTitle(),
                        booking.getUser().getFirstname()+" "+ booking.getUser().getLastname(),
                        "к сожалению отклонена",
                        "Ждем вас на других курсах")
        );
    }
}

