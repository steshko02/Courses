package com.example.coursach.dto.user;

import com.example.coursach.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@Jacksonized
public class RecoveryRequestDto {

    @NotBlank(message = ErrorCode.WEAK_EMAIL)
    @Email(message = ErrorCode.WEAK_EMAIL)
    private final String email;

}
