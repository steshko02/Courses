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

    @Size(max = 64)
    private final String department;

    @Size(min = 13, max = 20)
    private final String phoneNumber;
    // regex
    @Size(max = 160)
    private final String githubUrl;
    // jobTitle
    @Size(max = 60)
    private final String jobTitle;

    @Size(max = 256)
    private final String other;

    @Size(max = 256)
    private final String experience;

    @Size(min = 2, max = 64)
    private String firstname;

    @Size(min = 2, max = 64)
    private String lastname;

    @Size(min = 2, max = 64)
    private String email;

}
