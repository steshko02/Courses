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
public class UpdateProfileDto {

    @NotBlank(message = ErrorCode.WEAK_NICKNAME)
    @Size(max = 20, message = ErrorCode.WEAK_NICKNAME)
    private final String nickname;
    // add validation
    @Size(max = 64)
    private final String department;
    // regex
    @Size(max = 160)
    private final String githubUrl;
    // jobTitle
    @Size(max = 60)
    private final String jobTitle;

    @Size(max = 60)
    private final String phoneNumber;

    @Size(max = 256)
    private final String other;

}
