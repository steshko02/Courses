package com.example.coursach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ProfileUserDto {

    private final Long id;

    private final String photoUrl;

    private final String department;

    private final String jobTitle;

    private final String other;

    private final String githubUrl;

    private final String userId;
}
