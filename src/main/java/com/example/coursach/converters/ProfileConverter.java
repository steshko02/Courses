
package com.example.coursach.converters;

import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.entity.Credential;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.UserStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProfileConverter {

    public Profile toEntity(ProfileUserDto profileUserDto) {

        return Profile.builder()
                .photoUrl(profileUserDto.getPhotoUrl())
                .other(profileUserDto.getOther())
                .department(profileUserDto.getDepartment())
                .githubUrl(profileUserDto.getGithubUrl())
                .jobTitle(profileUserDto.getJobTitle())
                .build();
    }

    public ProfileUserDto toDto(Profile profile) {

        return ProfileUserDto.builder()
                .photoUrl(profile.getPhotoUrl())
                .other(profile.getOther())
                .department(profile.getDepartment())
                .githubUrl(profile.getGithubUrl())
                .jobTitle(profile.getJobTitle())
                .build();
    }
}
