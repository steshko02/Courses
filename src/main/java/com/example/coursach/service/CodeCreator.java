package com.example.coursach.service;

import com.example.coursach.entity.Code;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.CodeType;
import com.example.coursach.utility.Generator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodeCreator {

    public Code create(User user){
        return Code.builder()
                .code(Generator.generateSixDigitalCode())
                .requested(user)
                .type(CodeType.AUTH)
                .dateCreation(LocalDateTime.now())
                .build();
    }
}
