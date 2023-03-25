package com.example.coursach.dto.profile;

import com.example.coursach.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@Jacksonized
public class CreateProfileDto {

    @NotBlank(message = ErrorCode.WEAK_NICKNAME)
    @Size(max = 20, message = ErrorCode.WEAK_NICKNAME)
    private final String nickname;

}
