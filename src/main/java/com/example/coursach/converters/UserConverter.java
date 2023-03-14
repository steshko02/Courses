package com.example.coursach.converters;

import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.entity.Credential;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@AllArgsConstructor
public class UserConverter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User toEntityWithoutRole(RegistrationUserDto userDto) {

        return User.builder()
                .firstname(userDto.getFirstname())
                .lastname(userDto.getLastname())
                .credential(
                        Credential.builder()
                                .email(userDto.getEmail())
                                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                                .build()
                )
                .roles(new HashSet<>())
                .status(UserStatus.NOT_ACTIVE)
                .build();
    }
}
