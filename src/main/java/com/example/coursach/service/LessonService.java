package com.example.coursach.service;

import com.example.coursach.converters.AnswerConverter;
import com.example.coursach.converters.CheckWorkConvertor;
import com.example.coursach.converters.LessonConverter;
import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.*;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.*;
import com.example.coursach.service.converter.UserConverter;
import com.example.coursach.service.model.mail.Notification;
import com.example.coursach.service.model.mail.enums.MailScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonConverter lessonConverter;
    private final CourseRepository courseRepository;
    private final ResourceRepository repository;
    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final WorkConverter workConverter;
    private final CourseUserRepository courseUserRepository;
    private final AnswerRepository answerRepository;
    private final AnswerConverter answerConverter;
    private final CheckWorkRepository checkWorkRepository;
    private final CheckWorkConvertor checkWorkConvertor;
    private final UserService userService;
    private final EmailSenderService postman;
    private final CurrentUserRequestLocaleService currentUserRequestLocaleService;


    public Long createLesson(LessonDto lessonDto, String uuid) throws AccessDeniedException {

        Optional<User> userById = userRepository.findUserById(uuid);

        if (userService.getByRoleOnCourse(uuid, lessonDto.getCourseId(), UserRole.LECTURER).isEmpty()
                && userById.get().getRoles().stream().noneMatch(x -> x.getName().equals(UserRole.ADMIN))) {
            throw new AccessDeniedException("You has not permission for this operation");
        }
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);
        List<User> userList = userRepository.findAllByIds(lessonDto.getUserIds());
        Lesson entity = lessonConverter.toEntity(lessonDto, course, userList);

        if (lessonDto.getId() != null) {
            Optional<Lesson> byId = lessonRepository.findById(lessonDto.getId());

            byId.ifPresent(
                    lesson -> {
                        lesson.setDescription(entity.getDescription());
                        lesson.setStatus(entity.getStatus());
                        lesson.setTitle(entity.getTitle());
                        lesson.setStart(entity.getStart());
                        lesson.setEnd(entity.getEnd());
                        lesson.setMentors(entity.getMentors());
                        lesson.setResources(lessonDto.getResourceDtos().stream().map(
                                resourceDto -> Resource.builder()
                                        .id(resourceDto.getId())
                                        .extension(resourceDto.getExtension())
                                        .url(resourceDto.getUrl())
                                        .filename(resourceDto.getFilename())
                                        .build()
                        ).collect(Collectors.toList()));
                        lessonRepository.save(lesson);
                    }
            );
            return byId.get().getId();
        } else {
            return lessonRepository.save(entity).getId();
        }
    }

    public LessonDtoWithMentors getById(Long id, String userUuid) {
        Lesson lesson = lessonRepository.findById(id).get();

        Work work = lesson.getWork();

        List<BaseUserInformationDto> baseUserInformationDtos
                = userConverter.listUserToListBaseUserInformationDto(lesson.getMentors());

        WorkDto workDto = Optional.ofNullable(work).map(workConverter::toDto).orElse(null);
        LessonDtoWithMentors lessonDtoWithMentors = lessonConverter.toDto(lesson, baseUserInformationDtos, Optional.ofNullable(workDto));

        Optional<CourseUser> byId = courseUserRepository.findById(UserCourseId.builder().courseId(lesson.getCourse().getId()).userId(userUuid).build());

        byId.ifPresent(x -> {
            if (byId.get().getRole().getName().equals(UserRole.STUDENT)) {
                lessonDtoWithMentors.setStudentId(x.getId().getUserId());
                lessonDtoWithMentors.setAnswer(
                        Optional.ofNullable(work).map(w ->
                                {
                                    Answer byWork_idAndUser_id = answerRepository.findByWork_IdAndUser_Id(w.getId(), userUuid);
                                    if (byWork_idAndUser_id == null)
                                        return null;
                                    Optional<CheckWork> byAnswerId = checkWorkRepository.findByAnswer_Id(byWork_idAndUser_id.getId());
                                    lessonDtoWithMentors.setCheckWork(
                                            byAnswerId.map(checkWorkConvertor::toDto).orElse(null)
                                    );
                                    return answerConverter.toDto(byWork_idAndUser_id);
                                }
                        ).orElse(null));
            }
            if (x.getRole().getName().equals(UserRole.STUDENT))
                lessonDtoWithMentors.setStudentId(userUuid);
            else if (x.getRole().getName().equals(UserRole.LECTURER))
                lessonDtoWithMentors.setMentorId(userUuid);
        });
        return lessonDtoWithMentors;
    }


    public void update(LessonDto lessonDto, String uuid) throws AccessDeniedException {
        Optional<User> userById = userRepository.findUserById(uuid);
        if (userService.getByRoleOnCourse(uuid, lessonDto.getCourseId(), UserRole.LECTURER).isEmpty()
                && userById.get().getRoles().stream().noneMatch(x -> x.getName().equals(UserRole.ADMIN))) {
            throw new AccessDeniedException("You has not permission for this operation");
        }

        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        List<User> userList = userRepository.findAllByIds(lessonDto.getUserIds());

        Lesson newLesson = lessonConverter.toEntity(lessonDto, course, userList);
        newLesson.setId(lessonDto.getId());

        lessonRepository.save(newLesson);
    }

    @Transactional
    public Long deleteById(Long id, String uuid) throws AccessDeniedException {

        Optional<User> userById = userRepository.findUserById(uuid);
        if (userService.getByRoleAndLessonOnCourse(uuid, id, UserRole.LECTURER).isEmpty()
                || userById.get().getRoles().stream().noneMatch(x -> x.getName().equals(UserRole.ADMIN))) {
            throw new AccessDeniedException("You has not permission for this operation");
        }
        lessonRepository.deleteById(id);
        return id;
    }

    public void checkAndSwitchStatus() {
        LocalDateTime now = LocalDateTime.now();
        lessonRepository.updateLessonByTime(now);
    }

    public void checkAndSendMessages() {

        List<Lesson> byStartBetween = lessonRepository.findByStartBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        byStartBetween.forEach(lesson -> {
            courseUserRepository.findById_CourseId(lesson.getCourse().getId())
                    .forEach(uc -> sendMessage(userRepository.findById(uc.getId().getUserId()).get(),lesson));
        });
    }

    public void sendMessage(User user, Lesson lesson) {

        Course course = lesson.getCourse();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm");
        String dateString = simpleDateFormat.format(java.sql.Timestamp.valueOf(lesson.getStart()));
        postman.send(
                Notification.buildLessonNotification(
                        currentUserRequestLocaleService.getCurrentLocale(),
                        MailScope.LESSON_START,
                        user.getEmail(),
                        course.getTitle(),
                        user.getFirstname() + " " + user.getLastname(),
                        lesson.getTitle(),
                        dateString)
        );
    }
}
