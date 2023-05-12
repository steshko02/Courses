package com.example.coursach.repository;

import com.example.coursach.entity.Course;
import com.example.coursach.entity.enums.TimeStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findAll(Pageable pageable);


    @Modifying
    @Query("update Course l set l.status =" +
            " case when l.start <= ?1 and l.end > ?1 then 1" +
            " when l.end <= ?1 then 2" +
            " else 0 end ")
    void updateCourseByTime(LocalDateTime time);

    Page<Course> findByStatus(PageRequest of, TimeStatus status);

    Page<Course> findByIdIn(List<Long> ids, PageRequest of);
}
