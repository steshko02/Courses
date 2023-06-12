package com.example.coursach.repository;

import com.example.coursach.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {

    Answer findByWork_IdAndUser_Id(Long workId, String userUuid);

    Page<Answer> findByWork_Id(Long id, PageRequest of);

}

