package com.example.coursach.service;

import com.example.coursach.converters.AnswerConverter;
import com.example.coursach.converters.CheckWorkConvertor;
import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.*;
import com.example.coursach.entity.*;
import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.repository.*;
import com.example.coursach.repository.filter.AnswerSpecification;
import com.example.coursach.service.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final WorkRepository workRepository;
    private final WorkConverter workConverter;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final UserConverter userConverter;
    private final AnswerConverter answerConverter;
    private final CheckWorkRepository checkWorkRepository;
    private final CheckWorkConvertor checkWorkConvertor;

    public Long createAnswer(AnswerDto answerDto, String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(RuntimeException::new);
        Work work = workRepository.findById(answerDto.getWorkId()).orElseThrow(RuntimeException::new);

        //чекать на то что юзер - ученик на курсе
        Answer answer = Answer.builder()
                .comment(answerDto.getComment())
                .user(user)
                .work(work)
                .status(checkTime(work.getDeadline()))
                .dateCreation(LocalDateTime.now())
                .build();

        return answerRepository.save(answer).getId();
    }

    private TimeStatus checkTime(LocalDateTime deadline){
        if(LocalDateTime.now().isBefore(deadline))
            return TimeStatus.DURING;
        return TimeStatus.FINISHED;
    }

    public PaginationAnswerDto getByLesson(Integer number, Integer size, Long id, String user, String status) {
        //сделать проверку на ментора

        Lesson lesson = lessonRepository.findById(id).orElseThrow(RuntimeException::new);

        if(lesson.getWork()==null){
            return PaginationAnswerDto.builder()
                    .totalCount(0L)
                    .currentPage(number + 1)
                    .totalPages(0)
                    .answers(new ArrayList<>())
                    .build();
        }

        Page<Answer> answers = answerRepository.findAll(
                AnswerSpecification.creatAnswerSpecification(lesson.getWork().getId(), user,
                        TimeStatus.byString(status)),
                PageRequest.of(number, size));
//        Page<Answer> answers = answerRepository.findByWork_Id(lesson.getWork().getId(), PageRequest.of(number, size));

        Map<Long, CheckWork> checkWorkMap = checkWorkRepository.findAllByAnswer_IdIn(answers.get().map(Answer::getId).collect(Collectors.toList()))
                .stream().collect(
                        Collectors.toMap(c->c.getAnswer().getId(), Function.identity()));

        List<AnswerWithUserDto> collect = answers.stream().map(a -> AnswerWithUserDto.builder()
                .date(a.getDateCreation())
                .timeStatus(a.getStatus())
                .result(Optional.ofNullable(checkWorkMap.get(a.getId())).map(checkWorkConvertor::toDto).orElse(null))
                .resource(a.getResources().stream().map(r -> ResourceDto.builder()
                        .filename(r.getFilename())
                        .extension(r.getExtension())
                        .url(r.getUrl())
                        .id(r.getId())
                        .build()).collect(Collectors.toList()))
                .comment(a.getComment())
                .workId(a.getWork().getId())
                .user(userConverter.userToBaseUserInformationDto(a.getUser()))
                .id(a.getId())
                .build()
        ).toList();

        return PaginationAnswerDto.builder()
                .totalCount(answers.getTotalElements())
                .currentPage(number + 1)
                .totalPages(answers.getTotalPages())
                .answers(collect)
                .build();
    }

    public Long update(AnswerDto answerDto, String uuid) {
        User user = userRepository.findById(uuid).orElseThrow(RuntimeException::new);
        Work work = workRepository.findById(answerDto.getWorkId()).orElseThrow(RuntimeException::new);

        //чекать на то что юзер - ученик на курсе
        Answer answer = Answer.builder()
                .id(answerDto.getId())
                .comment(answerDto.getComment())
                .user(user)
                .work(work)
                .status(checkTime(work.getDeadline()))
                .dateCreation(LocalDateTime.now())
                .build();

        return answerRepository.save(answer).getId();
    }
}
