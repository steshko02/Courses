package com.example.coursach.repository;

import com.example.coursach.entity.Booking;
import com.example.coursach.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
