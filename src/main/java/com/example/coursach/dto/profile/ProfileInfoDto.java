package com.example.coursach.dto.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@Setter
public class ProfileInfoDto {

    private String firstname;

    private String lastname;

    private String email;

    private final String number;

    private final String photoUrl;

    private final String department;

    private final String githubUrl;

    private final String jobTitle;

    private final String other;

    private final String experience;


}
