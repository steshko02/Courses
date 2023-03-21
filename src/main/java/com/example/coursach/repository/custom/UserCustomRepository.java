package com.example.coursach.repository.custom;

import com.example.coursach.entity.User;

import com.example.coursach.entity.enums.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserCustomRepository {

    Page<User> findAllByStatusAndEmailOrNickname(String query, AccountStatus accountStatus, Pageable pageable);

    Optional<User> findUserByIdWithFetchProfile(String uuid);

    Optional<User> findUserByEmailWithFetchProfile(String email);

    Optional<User> findUserByIdWithFetchNotification(String userUuid);

    List<User> findAllByIds(List<String> userIds);


}
