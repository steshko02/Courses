package com.example.coursach.service;

import com.example.coursach.converters.CheckWorkConvertor;
import com.example.coursach.dto.CheckWorkDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.CheckWork;
import com.example.coursach.entity.User;
import com.example.coursach.repository.AnswerRepository;
import com.example.coursach.repository.CheckWorkRepository;
import com.example.coursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckWorkService {
    private final CheckWorkRepository checkWorkRepository;
    private final CheckWorkConvertor checkWorkConvertor;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public Long createAnswer(CheckWorkDto checkWorkDto, String uuid) {

        User user = userRepository.findUserById(uuid).orElseThrow(RuntimeException::new);

        Answer answer = answerRepository.findById(checkWorkDto.getAnswerId()).orElseThrow(RuntimeException::new);

        CheckWork checkWork = checkWorkRepository.findByAnswer_Id(answer.getId()).map(
                x -> {
                    x.setComment(checkWorkDto.getComment());
                    x.setMark(checkWorkDto.getMark());
                    x.setMentor(user);
                    return x;
                }
        ).orElse(checkWorkConvertor.toEntity(checkWorkDto, answer, user));

        //проверка на ментора и курс!!

        return checkWorkRepository.save(checkWork).getId();
    }
}
