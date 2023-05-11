package com.example.coursach.repository;

import com.example.coursach.entity.Answer;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Answer findByWork_IdAndUser_Id(Long workId, String userUuid);
}
