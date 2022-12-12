package com.example.coursach.service;

import com.example.coursach.converters.ProfileConverter;
import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.User;
import com.example.coursach.repository.ProfileRepository;
import com.example.coursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileConverter profileConverter;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    public Long createProfile(ProfileUserDto profileUserDto) {

        User user = userRepository.findById(profileUserDto.getUserId())
                .orElseThrow(RuntimeException::new);

        Profile save = profileConverter.toEntity(profileUserDto);

        user.setProfile(save);
       return userRepository.save(user).getProfile().getId();
    }

    public void update(ProfileUserDto profileUserDto) {
        User user = userRepository.findById(profileUserDto.getUserId())
                .orElseThrow(RuntimeException::new);

        Profile newProfile = profileConverter.toEntity(profileUserDto);
        newProfile.setId(profileUserDto.getId());
        user.setProfile(newProfile);
        userRepository.save(user);
        profileRepository.save(newProfile);
    }

    public ProfileUserDto getById(Long id) {
       return profileConverter.toDto(profileRepository.findById(id).get());
    }
}
