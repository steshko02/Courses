package com.example.coursach.repository;

import com.example.coursach.entity.CourseUser;
import com.example.coursach.entity.Credential;
import com.example.coursach.entity.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseUserRepository extends JpaRepository<CourseUser, UserCourseId> {

}
