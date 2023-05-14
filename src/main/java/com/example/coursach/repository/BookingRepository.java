package com.example.coursach.repository;

import com.example.coursach.entity.Booking;
import com.example.coursach.entity.enums.BookingStatus;
import com.example.coursach.repository.filter.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

//    Page<Booking> findAll(Pageable pageable);

    Booking findByUser_Id(String userId);

    Optional<Booking> findByUser_IdAndCourseId(String userId, Long courseId);

    Page<Booking> findAllByStatus(BookingStatus status, PageRequest of);

//    Page<Booking> findAll(Specification<Booking> createBookingSpecification, Pageable pageable);
}
