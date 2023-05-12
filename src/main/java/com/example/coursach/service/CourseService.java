package com.example.coursach.service;

import com.example.coursach.converters.CourseConverter;
import com.example.coursach.dto.CourseDto;
import com.example.coursach.dto.CourseDtoForMentors;
import com.example.coursach.dto.CourseDtoWithMentors;
import com.example.coursach.dto.CourseShortInfoDto;
import com.example.coursach.dto.PaginationCoursesDto;
import com.example.coursach.dto.PaginationCoursesWithMentorsDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Role;
import com.example.coursach.entity.User;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.enums.FilterBy;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                        .role(Role.builder().name(UserRole.LECTURER).build())
                        .build();
                courseUserRepository.save(courseUser);
            });
        }
        return save.getId();
    }

    public List<CourseDto> getAll() {
        return courseConverter
                .toDtos(courseRepository.findAll());
    }

    public CourseDtoWithMentors getById(Long id, String userUuid) {
        List<CourseUser> allByUserCourseIdCourseId = courseUserRepository.findById_CourseId(id);

        List<User> users = userRepository.
                findAllByIds(allByUserCourseIdCourseId.stream()
                        .map(x -> x.getId().getUserId()).collect(Collectors.toList()));

        List<BaseUserInformationDto> baseUserInformationDtos = userConverter.listUserToListBaseUserInformationDto(users);

        CourseDtoWithMentors courseDtoWithMentors = courseConverter.toDtoWithLessonAndMentors(courseRepository.findById(id).get(), baseUserInformationDtos);
        Optional<CourseUser> byId = courseUserRepository.findById(UserCourseId.builder().courseId(id).userId(userUuid).build());

        byId.ifPresent(x -> courseDtoWithMentors.setStudentId(x.getId().getUserId()));
        return courseDtoWithMentors;
    }

    public void update(CourseDto courseDto) {
        Course newCourse = courseConverter.toEntity(courseDto);
        newCourse.setId(courseDto.getId());
        courseRepository.save(newCourse);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
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

    public PaginationCoursesDto getAllWithPaginationWithFiltering(Integer number, Integer size, FilterBy filter) {

        Page<Course> coursePage = null;

        switch (filter) {
            case ALL -> coursePage = courseRepository.findAll(PageRequest.of(number, size));
            case DURING -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.DURING);
            case NOT_STARTED -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.NOT_STARTED);
            case FINISHED -> coursePage = courseRepository.findByStatus(PageRequest.of(number, size), TimeStatus.FINISHED);
//            case DELETED -> coursePage = courseRepository.findAllByStatus(PageRequest.of(number, size), TimeStatus.NOT_STARTED);
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

    public PaginationCoursesWithMentorsDto getAllByMentor(Integer number, Integer size, String uuid) {

        User user = userRepository.findById(uuid).orElseThrow(RuntimeException::new);

        List<CourseUser> byId_userId = Optional.ofNullable(
                        courseUserRepository.findById_UserIdAndAndRole_Name(uuid, UserRole.LECTURER))
                .orElse(Lists.newArrayList());

        //переделать
        if (byId_userId.isEmpty()) {
            throw new RuntimeException();
        }

        List<Long> courses = byId_userId.stream().map(cu -> cu.getId().getCourseId()).collect(Collectors.toList());

        Page<Course> byAllById = courseRepository.findByIdIn(courses, PageRequest.of(number, size));
        Map<Long, List<User>> collect = byAllById.getContent().stream().collect(
                Collectors.toMap(Course::getId, c -> Optional.ofNullable(
                                courseUserRepository.findById_CourseId(c.getId()))
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
}
