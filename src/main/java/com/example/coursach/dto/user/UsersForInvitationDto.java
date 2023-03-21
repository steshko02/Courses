package com.example.coursach.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Jacksonized
@Builder
public class UsersForInvitationDto {

    private final List<UserForSharedItemDto> coowners;

}
