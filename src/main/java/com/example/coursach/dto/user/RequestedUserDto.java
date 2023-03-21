package com.example.coursach.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class RequestedUserDto {

    private final String uuid;

    private final String nickname;

    private final String hexColor;

}
