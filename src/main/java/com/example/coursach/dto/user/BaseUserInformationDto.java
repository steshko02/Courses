package com.example.coursach.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class BaseUserInformationDto {

    private final String uuid;

    private final String email;

    private final String firstname;

    private final String lastname;
}
