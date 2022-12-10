package com.example.coursach.service;

import com.example.coursach.converters.ProfileConverter;
import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileConverter profileConverter;
    private final ProfileRepository profileRepository;

    public void createProfile(ProfileUserDto profileUserDto) {
        profileRepository.save(profileConverter.toEntity(profileUserDto));
    }

    public void update(ProfileUserDto profileUserDto) {
        Profile newProfile = profileConverter.toEntity(profileUserDto);
        newProfile.setId(profileUserDto.getId());

        profileRepository.save(newProfile);
    }

    public ProfileUserDto getById(Long id) {
       return profileConverter.toDto(profileRepository.findById(id).get());
    }
}
