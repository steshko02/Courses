package com.example.coursach.dto.security;

import com.example.coursach.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Jacksonized
@Builder
public class RegisterConfirmDto {

    @NotBlank(message = ErrorCode.WEAK_EMAIL)
    @Email(message = ErrorCode.WEAK_EMAIL)
    private final String email;

    private final String password;

    private final String code;
}
