package com.example.coursach.service;

import com.example.coursach.dto.profile.CreateProfileDto;
import com.example.coursach.dto.profile.ProfileInfoDto;
import com.example.coursach.dto.profile.UpdateProfileDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.exception.profile.ProfileAlreadyExistException;
import com.example.coursach.exception.profile.ProfileNotFoundException;
import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.repository.ProfileRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.ProfileConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileConverter profileConverter;

    private final UserRepository userRepository;

    public ProfileService(ProfileRepository profileRepository,
                          ProfileConverter profileConverter,
                          UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.profileConverter = profileConverter;
        this.userRepository = userRepository;
    }

    @Transactional
    public void createProfile(CreateProfileDto profileDto, String authorizedUserUuid) {
        if (profileRepository.findById(authorizedUserUuid).isPresent()) {
            throw new ProfileAlreadyExistException();
        }

        Profile profile = profileConverter.toEntity(profileDto);
        profile.setUser(
                userRepository.findById(authorizedUserUuid).orElseThrow(UserNotFoundException::new)
        );
        profileRepository.save(profile);
    }

    @Transactional
    public void updateProfile(UpdateProfileDto profileDto, String authorizedUserUuid) {
        Profile profile = profileRepository.findById(authorizedUserUuid).orElseThrow(ProfileNotFoundException::new);
        profile.setNickname(profileDto.getNickname());
        profileRepository.save(profile);
    }

    @Transactional(readOnly = true)
    public ProfileInfoDto getProfile(String authorizedUserUuid) {
        Profile profile = profileRepository.findById(authorizedUserUuid).orElseThrow(ProfileNotFoundException::new);
        return profileConverter.toDto(profile);
    }

}
