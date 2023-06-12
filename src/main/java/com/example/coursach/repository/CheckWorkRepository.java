package com.example.coursach.repository;

import com.example.coursach.entity.CheckWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckWorkRepository extends JpaRepository<CheckWork, Long> {
    List<CheckWork> findAllByAnswer_IdIn(List<Long> ids);

    Optional<CheckWork> findByAnswer_Id(Long id);

//    CheckWork findByWork_IdAndUser_Id(Long workId, String userUuid);
//
//    Page<CheckWork> findByWork_Id(Long id, PageRequest of);

}

