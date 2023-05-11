package com.example.coursach.service;

import com.example.coursach.converters.WorkConverter;
import com.example.coursach.dto.AnswerDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import com.example.coursach.entity.enums.TimeStatus;
import com.example.coursach.repository.AnswerRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.WorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final WorkRepository workRepository;
    private final WorkConverter workConverter;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

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
        if(LocalDateTime.now().isAfter(deadline))
            return TimeStatus.DURING;
        return TimeStatus.FINISHED;
    }
}
