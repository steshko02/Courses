package com.example.coursach.repository;

import com.example.coursach.entity.Code;
import com.example.coursach.entity.enums.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<Code, Long> {

    Code findByCodeAndRequestedCredentialEmailAndType(Integer code, String requestedCredentialEmail, CodeType codeType);

}
