package com.example.coursach.dto.user.invite;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class InviteResponseDto {

    private final String uuid;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String nickname;

    private final String email;

    private final String photoUrl;

    private final String invitationCode;

}
