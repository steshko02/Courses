package com.example.coursach.dto.user.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class LoginRequestDto {

    public static final LoginRequestDto EMPTY = LoginRequestDto.builder().build();

    @JsonProperty(value = "email")
    private final String email;

    @JsonProperty(value = "password")
    private final String password;

}
