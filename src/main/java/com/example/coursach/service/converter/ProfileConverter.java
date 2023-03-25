package com.example.coursach.service.converter;

import com.example.coursach.dto.profile.CreateProfileDto;
import com.example.coursach.dto.profile.ProfileInfoDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileConverter {

    public static Profile getProfileOrEmptyProfile(User invitedUser) {
        return Optional.ofNullable(invitedUser.getProfile()).orElse(Profile.builder().build());
    }

    public Profile toEntity(CreateProfileDto createProfileDto) {
        return Profile
                .builder()
                .nickname(createProfileDto.getNickname())
                .build();
    }

    public ProfileInfoDto toDto(Profile profile) {
        return ProfileInfoDto
                .builder()
                .nickname(profile.getNickname())
                .photoUrl(profile.getPictureFormat())
                .build();
    }

}
