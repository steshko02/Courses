package com.example.coursach.service;

import com.example.coursach.dto.profile.CreateProfileDto;
import com.example.coursach.dto.profile.ProfileInfoDto;
import com.example.coursach.dto.profile.UpdateProfileDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.User;
import com.example.coursach.exception.profile.ProfileAlreadyExistException;
import com.example.coursach.exception.profile.ProfileNotFoundException;
import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.repository.ProfileRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.service.converter.ProfileConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<User> byId = userRepository.findById(authorizedUserUuid);
        profile.setUser(
                byId.orElseThrow(UserNotFoundException::new)
        );
        byId.get().setProfile(profile);
        userRepository.save(byId.get());
    }

    @Transactional
    public void updateProfile(UpdateProfileDto profileDto, String authorizedUserUuid) {

        Profile profile = profileRepository.findById(authorizedUserUuid).orElseThrow(ProfileNotFoundException::new);

        profile = profileConverter.toEntity(profileDto);
        profile.setUserId(authorizedUserUuid);

        Optional<User> user = userRepository.findById(authorizedUserUuid);
        user.ifPresent(x -> {
            x.setFirstname(profileDto.getFirstname());
            x.setLastname(profileDto.getLastname());
            x.setEmail(profileDto.getEmail());
        });

        if (user.isPresent()) {
            profile.setUser(user.get());
            profileRepository.save(profile);
            user.get().setProfile(profile);
            userRepository.save(user.get());
        }

    }

    @Transactional(readOnly = true)
    public ProfileInfoDto getProfile(String authorizedUserUuid) {
        Optional<Profile> profile = profileRepository.findById(authorizedUserUuid);
        ProfileInfoDto dto = null;
        if (profile.isPresent()) {
            dto = profileConverter.toDto(profile.get());
            dto.setEmail(profile.get().getUser().getEmail());
            dto.setLastname(profile.get().getUser().getLastname());
            dto.setFirstname(profile.get().getUser().getFirstname());
            dto.setId(profile.get().getUserId());
        } else {
            Optional<User> userById = userRepository.findUserById(authorizedUserUuid);
            if (userById.isPresent()) {
                dto = ProfileInfoDto.builder().build();
                dto.setEmail(userById.get().getEmail());
                dto.setLastname(userById.get().getLastname());
                dto.setFirstname(userById.get().getFirstname());
            }
        }
        return dto;
    }

}
