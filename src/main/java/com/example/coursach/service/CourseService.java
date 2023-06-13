package com.example.coursach.service;

import com.example.coursach.converters.CourseConverter;
import com.example.coursach.dto.*;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.*;
import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.entity.enums.FilterBy;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.BookingRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.RoleRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.UserConverter;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseConverter courseConverter;
    private final UserConverter userConverter;
    private final CourseRepository courseRepository;
    private final CourseUserRepository courseUserRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BookingRepository bookingRepository;
    private final EmailSenderService postman;
    private final CurrentUserRequestLocaleService currentUserRequestLocaleService;

    @Transactional(rollbackFor = RuntimeException.class)
    public Long createCourse(CourseDto courseDto) {

        Course save = courseRepository.save(courseConverter.toEntity(courseDto));

        List<String> ids = courseDto.getIds();
        if (ids != null && !ids.isEmpty()) {
            ids.forEach(u -> {

                UserCourseId courseId = UserCourseId.builder()
                        .userId(u)
                        .courseId(save.getId())
                        .build();

                CourseUser courseUser = CourseUser.builder()
                        .id(courseId)
                        .role(roleRepository.findByName(UserRole.LECTURER))
                        .build();
                courseUserRepository.save(courseUser);
            });
        }
        return save.getId();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Long updateCourse(CourseDto courseDto) {

        Course course = courseRepository.findById(courseDto.getId()).orElseThrow(RuntimeException::new);
        Course newCourse = courseConverter.toEntity(courseDto);

        List<String> ids = courseDto.getIds();
        if (ids != null && !ids.isEmpty()) {

            List<CourseUser> byId_courseId =
                    courseUserRepository.findById_CourseIdAndAndRole_Name(course.getId(),UserRole.LECTURER);
            courseUserRepository.deleteAll(byId_courseId);

            ids.forEach(u -> {

                UserCourseId courseId = UserCourseId.builder()
                        .userId(u)
                        .courseId(course.getId())
                        .build();

                CourseUser courseUser = CourseUser.builder()
                        .id(courseId)
                        .role(roleRepository.findByName(UserRole.LECTURER))
                        .build();
                courseUserRepository.save(courseUser);
            });
        }

        course.setSize(newCourse.getSize());
        course.setTitle(newCourse.getTitle());
        course.setDescription(newCourse.getDescription());
        course.setLessons(newCourse.getLessons());
        course.setStart(newCourse.getStart());
        course.setEnd(newCourse.getEnd());
        return course.getId();
    }

    public List<CourseDto> getAll() {
        return courseConverter
                .toDtos(courseRepository.findAll());
    }

    public CourseDtoWithMentors getById(Long id, String userUuid) {

        User userById = userRepository.findUserById(userUuid).orElseThrow(RuntimeException::new);

        List<CourseUser> allByUserCourseIdCourseId = courseUserRepository.findById_CourseIdAndRole_Name(id,UserRole.LECTURER);

        List<User> users = userRepository.
                findAllByIds(allByUserCourseIdCourseId.stream()
                        .map(x -> x.getId().getUserId()).collect(Collectors.toList()));

        List<BaseUserInformationDto> baseUserInformationDtos = userConverter.listUserToListBaseUserInformationDto(users);

        Course course = courseRepository.findById(id).get();

        Optional<CourseUser> byId
                = courseUserRepository.findById(UserCourseId.builder().courseId(id).userId(userUuid).build());

        UserRole name = byId.map(x->x.getRole().getName()).orElse(UserRole.USER);

        boolean access = name.equals(UserRole.STUDENT)
                || name.equals(UserRole.LECTURER)
                || userById.getRoles().stream().anyMatch(x -> x.getName().equals(UserRole.ADMIN));

        CourseDtoWithMentors courseDtoWithMentors
                = courseConverter.toDtoWithLessonAndMentors(course,
                access ? course.getLessons(): new ArrayList<>(),
                baseUserInformationDtos);

        if(name.equals(UserRole.STUDENT))
            courseDtoWithMentors.setStudentId(userUuid);
        else if(name.equals(UserRole.LECTURER))
            courseDtoWithMentors.setMentorId(userUuid);


        Optional<Booking> byUser_idAndCourseId = bookingRepository.findByUser_IdAndCourseId(userUuid, course.getId());
        byUser_idAndCourseId.ifPresent( b -> {
            courseDtoWithMentors.setBookingStatus(b.getStatus());
                }
        );

        return courseDtoWithMentors;
    }

    public void update(CourseDto courseDto) {
        Course newCourse = courseConverter.toEntity(courseDto);
        newCourse.setId(courseDto.getId());
        courseRepository.save(newCourse);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
        List<CourseUser> byId_courseId = courseUserRepository.findById_CourseId(id);
        courseUserRepository.deleteAll(byId_courseId);
}

    public PaginationCoursesDto getAllWithPagination(Integer number, Integer size) {

        Page<Course> coursePage = courseRepository.findAll(PageRequest.of(number, size));
        return PaginationCoursesDto.builder()
                .courseDtos(courseConverter
                        .toDtos(coursePage.get().collect(Collectors.toList())))
                .totalCount(coursePage.getTotalElements())
                .currentPage(number + 1)
                .totalPages(coursePage.getTotalPages())
                .build();
    }

    public void checkAndSwitchStatus() {
        LocalDateTime now = LocalDateTime.now();
        courseRepository.updateCourseByTime(now);
    }

    public PaginationCoursesDto getAllWithPaginationWithFiltering(Integer number, Integer size, FilterBy filter, String userId) {

        Page<Course> coursePage = null;

        switch (filter) {
            case ALL -> coursePage = courseRepository.findAll(PageRequest.of(number, size));
            case DURING -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.DURING);
            case NOT_STARTED -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.NOT_STARTED);
            case FINISHED -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.FINISHED);
            case  FOR_USER -> coursePage = getCourseByUserRole(userId,PageRequest.of(number, size), UserRole.STUDENT);
            case  FOR_MENTOR -> coursePage = getCourseByUserRole(userId,PageRequest.of(number, size), UserRole.LECTURER);
            default -> coursePage = courseRepository.findAll(PageRequest.of(number, size));
        }
        return PaginationCoursesDto.builder()
                .courseDtos(courseConverter
                        .toDtos(coursePage.get().collect(Collectors.toList())))
                .totalCount(coursePage.getTotalElements())
                .currentPage(number + 1)
                .totalPages(coursePage.getTotalPages())
                .build();

    }

    private Page<Course> getCourseByUserRole(String userId, PageRequest of, UserRole role) {
        User user = userRepository.findById(userId).orElseThrow(RuntimeException::new);

        List<CourseUser> byId_userId = Optional.ofNullable(
                        courseUserRepository.findById_UserIdAndRole_Name(userId, role))
                .orElse(Lists.newArrayList());

        List<Long> collect = byId_userId.stream().map(uc -> uc.getId().getCourseId()).collect(Collectors.toList());

        return courseRepository.findByIdIn(collect,of);
    }

    public PaginationCoursesWithMentorsDto getAllByRole(Integer number, Integer size, String uuid, UserRole role) {

        User user = userRepository.findById(uuid).orElseThrow(RuntimeException::new);

        List<CourseUser> byId_userId = Optional.ofNullable(
                        courseUserRepository.findById_UserIdAndRole_Name(uuid, role))
                .orElse(Lists.newArrayList());

        //переделать
        if (byId_userId.isEmpty()) {
            return PaginationCoursesWithMentorsDto.builder()
                    .courses(new ArrayList<>())
                    .totalCount(0L)
                    .currentPage(number + 1)
                    .totalPages(0)
                    .build();
        }

        List<Long> courses = byId_userId.stream().map(cu -> cu.getId().getCourseId()).collect(Collectors.toList());

        Page<Course> byAllById = courseRepository.findByIdIn(courses, PageRequest.of(number, size));
        Map<Long, List<User>> collect = byAllById.getContent().stream().collect(
                Collectors.toMap(Course::getId, c -> Optional.ofNullable(
                                courseUserRepository.findById_CourseIdAndAndRole_Name(c.getId(), UserRole.LECTURER))
                        .map(us -> userRepository.findAllByIds(us.stream().map(u -> u.getId().getUserId()).collect(Collectors.toList())))
                        .orElse(Lists.newArrayList())));

        List<CourseDtoForMentors> courseDtoForMentors = courseConverter
                .toDtosForMentors(byAllById.get().collect(Collectors.toList()),collect);

        return PaginationCoursesWithMentorsDto.builder()
                .courses(courseDtoForMentors)
                .totalCount(byAllById.getTotalElements())
                .currentPage(number + 1)
                .totalPages(byAllById.getTotalPages())
                .build();
    }

    public PaginationCoursesWithMentorsDto getAllByStudent(Integer number, Integer size, String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(RuntimeException::new);
        List<CourseUser> byId_userId = Optional.ofNullable(
                        courseUserRepository.findById_UserIdAndRole_Name(uuid, UserRole.LECTURER))
                .orElse(Lists.newArrayList());

        return null;
    }

    public void checkAndSendMessages() {

        List<Course> byStartBetween = courseRepository.findByStartBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        byStartBetween.forEach(course -> {
            courseUserRepository.findById_CourseId(course.getId())
                    .forEach(uc -> sendMessage(userRepository.findById(uc.getId().getUserId()).get(),course));
        });
    }

    public void sendMessage(User user, Course course) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm");
        String dateString = simpleDateFormat.format(java.sql.Timestamp.valueOf(course.getStart()));
        postman.send(
                Notification.buildCourseNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.COURSE_START,
                        user.getEmail(),
                        course.getTitle(),
                        user.getFirstname() + " " + user.getLastname(),
                        dateString)
        );
    }
}
