package com.example.coursach.repository;

import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRepository extends JpaRepository<Work, Long> {

}

