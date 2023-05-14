package com.example.coursach.repository;

import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Credential;
import com.example.coursach.entity.UserCourseId;
import com.example.coursach.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseUserRepository extends JpaRepository<CourseUser, UserCourseId> {

    List<CourseUser> findById_CourseId(Long courseId);
    List<CourseUser> findById_UserIdAndAndRole_Name(String userId, UserRole userRole);
    List<CourseUser> findById_CourseIdAndRole_Name(Long courseId,UserRole userRole);

    Boolean existsById_UserIdAndAndRole_Name(String userId, UserRole userRole);

    List<CourseUser> findById_CourseIdAndAndRole_Name(Long id, UserRole userRole);

}
