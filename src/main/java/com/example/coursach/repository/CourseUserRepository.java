package com.example.coursach.repository;

import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseUserRepository extends JpaRepository<CourseUser, UserCourseId> {

    List<CourseUser> findById_CourseId(Long courseId);
    List<CourseUser> findById_UserIdAndRole_Name(String userId, UserRole userRole);
    List<CourseUser> findById_CourseIdAndRole_Name(Long courseId,UserRole userRole);

    Boolean existsById_UserIdAndRole_Name(String userId, UserRole userRole);

    List<CourseUser> findById_CourseIdAndAndRole_Name(Long id, UserRole userRole);

    Optional<CourseUser> findById_CourseIdAndId_UserIdAndRole_Name(Long id, String userId, UserRole userRole);

}
