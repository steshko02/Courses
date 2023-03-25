package com.example.coursach.dto.profile;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ProfileInfoDto {

    private final String nickname;

    private final String photoUrl;

}
