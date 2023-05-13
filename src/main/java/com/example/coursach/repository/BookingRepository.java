package com.example.coursach.repository;

import com.example.coursach.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAll(Pageable pageable);

    Booking findByUser_Id(String userId);

    Optional<Booking> findByUser_IdAndCourseId(String userId, Long courseId);
}
