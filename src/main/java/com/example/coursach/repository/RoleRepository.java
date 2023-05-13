package com.example.coursach.repository;

import com.example.coursach.entity.Role;
import com.example.coursach.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(UserRole userRole);
}
