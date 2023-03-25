package com.example.coursach.dto.user.invite;

import com.example.coursach.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@Jacksonized
public class InviteRequestDto {

    @Size(min = 1, max = 20, message = ErrorCode.WEAK_NICKNAME)
    private final String nickname;

    @NotBlank(message = ErrorCode.WEAK_EMAIL)
    @Email(message = ErrorCode.WEAK_EMAIL)
    private final String email;

    @NotBlank(message = ErrorCode.UUID_CANT_BE_NULL)
    private final String itemUuid;

}
