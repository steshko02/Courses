package com.example.coursach.dto.user.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class JwtResponseDto {

    @JsonProperty(value = "jwt")
    private final String jwt;

}
