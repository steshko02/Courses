package com.example.coursach.repository;

import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.AccountStatus;
import com.example.coursach.repository.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository {

    Optional<User> findUserByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsAllByAccountStatusAndEmail(AccountStatus accountStatus, String email);

    Optional<User> findUserById(String id);
}
