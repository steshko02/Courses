package com.example.coursach.service;

import com.example.coursach.converters.AnswerConverter;
import com.example.coursach.converters.LessonConverter;
import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.LessonDto;
import com.example.coursach.dto.LessonDtoWithMentors;
import com.example.coursach.dto.WorkDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.User;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.Work;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.repository.AnswerRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.CourseUserRepository;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.ResourceRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public Long createLesson(LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        List<User> userList = userRepository.findAllByIds(lessonDto.getUserIds());

        return lessonRepository.save(lessonConverter.toEntity(lessonDto, course, userList)).getId();
    }

    public LessonDtoWithMentors getById(Long id, String userUuid) {
        Lesson lesson = lessonRepository.findById(id).get();

        Work work = lesson.getWork();

        List<BaseUserInformationDto> baseUserInformationDtos
                = userConverter.listUserToListBaseUserInformationDto(lesson.getMentors());

        WorkDto workDto = Optional.ofNullable(work).map(workConverter::toDto).orElse(null);
        LessonDtoWithMentors lessonDtoWithMentors = lessonConverter.toDto(lesson, baseUserInformationDtos, Optional.ofNullable(workDto));

        Optional<CourseUser> byId = courseUserRepository.findById(UserCourseId.builder().courseId(id).userId(userUuid).build());

        byId.ifPresent(x -> {
            if (byId.get().getRole().getName().equals(UserRole.STUDENT)) {
                lessonDtoWithMentors.setStudentId(x.getId().getUserId());
                lessonDtoWithMentors.setAnswer(
                        Optional.ofNullable(work).map(w ->
                                {
                                    Answer byWork_idAndUser_id = answerRepository.findByWork_IdAndUser_Id(w.getId(), userUuid);
                                    if (byWork_idAndUser_id == null)
                                        return null;
                                    return answerConverter.toDto(byWork_idAndUser_id);
                                }
                        ).orElse(null));
            }
        });
//убрать
        lessonDtoWithMentors.setAnswer(
                Optional.ofNullable(work).map(w ->
                        {
                            Answer byWork_idAndUser_id = answerRepository.findByWork_IdAndUser_Id(w.getId(), userUuid);
                            if (byWork_idAndUser_id == null)
                                return null;
                            return answerConverter.toDto(byWork_idAndUser_id);
                        }
                ).orElse(null));
        return lessonDtoWithMentors;
    }


    public void update(LessonDto lessonDto) {
        Course course = courseRepository.findById(lessonDto.getCourseId())
                .orElseThrow(RuntimeException::new);

        List<User> userList = userRepository.findAllByIds(lessonDto.getUserIds());

        Lesson newLesson = lessonConverter.toEntity(lessonDto, course, userList);
        newLesson.setId(lessonDto.getId());

        lessonRepository.save(newLesson);
    }

    public Long deleteById(Long id) {
        lessonRepository.deleteById(id);
        return id;
    }

    public void checkAndSwitchStatus() {
        LocalDateTime now = LocalDateTime.now();
        lessonRepository.updateLessonByTime(now);
    }
}
