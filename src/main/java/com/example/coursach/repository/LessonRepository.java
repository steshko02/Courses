package com.example.coursach.repository;

import com.example.coursach.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Modifying
    @Query("update Lesson l set l.status =" +
            " case when l.start <= ?1 and l.end > ?1 then 1" +
            " when l.end <= ?1 then 2" +
            " else 0 end ")
    void updateLessonByTime(LocalDateTime time);

}
