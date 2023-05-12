package com.example.coursach.repository;

import com.example.coursach.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface WorkRepository extends JpaRepository<Work, Long> {

    @Modifying
    @Query("update Work l set l.status =" +
            " case when ?1 <= l.deadline then 1" +
            "when ?1 > l.deadline then 2" +
            " else 0 end ")
    void updateWorkByTime(LocalDateTime time);

    void findByLesson_Id(Long id);
}

