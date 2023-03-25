package com.example.coursach.repository;

import com.example.coursach.entity.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code, Long> {

    Optional<Code> findCodeByRequestedEmail(String requestedEmail);

    Optional<Code> findCodeByRequestedEmailAndCode(String requestedEmail, Integer code);
}
