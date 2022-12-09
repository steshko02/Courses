package com.example.coursach.repository;

import com.example.coursach.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Code, Long> {

}
