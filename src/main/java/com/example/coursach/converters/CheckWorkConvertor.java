package com.example.coursach.converters;

import com.example.coursach.dto.CheckWorkDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.CheckWork;
import com.example.coursach.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class CheckWorkConvertor {
    public CheckWork toEntity(CheckWorkDto checkWorkDto, Answer answer, User user) {
        return CheckWork.builder()
                .comment(checkWorkDto.getComment())
                .answer(answer)
                .mentor(user)
                .dateCreation(LocalDateTime.now())
                .mark(checkWorkDto.getMark())
                .build();

    }

    public CheckWorkDto toDto(CheckWork checkWork) {
        return CheckWorkDto.builder()
                .id(checkWork.getId())
                .date(checkWork.getDateCreation())
                .answerId(checkWork.getAnswer().getId())
                .comment(checkWork.getComment())
                .mark(checkWork.getMark())
                .build();
    }
}
