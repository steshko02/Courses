package com.example.coursach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RegistrationUserDto {

    private final String firstname;

    private final String lastname;

    private final String email;

    private final String password;
}
