package com.example.coursach.controllers;

import com.example.coursach.dto.picture.PictureDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.dto.profile.CreateProfileDto;
import com.example.coursach.dto.profile.ProfileInfoDto;
import com.example.coursach.dto.profile.UpdateProfileDto;
import com.example.coursach.security.model.AuthorizedUser;
import com.example.coursach.service.MinioStorageService;
import com.example.coursach.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("profile")
public class ProfileController {

    private final ProfileService profileService;

    private final MinioStorageService minioStorageService;

    public ProfileController(
            ProfileService profileService,
            MinioStorageService minioStorageService) {
        this.profileService = profileService;
        this.minioStorageService = minioStorageService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createProfile(@RequestBody CreateProfileDto profileDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        profileService.createProfile(profileDto, authorizedUser.getUuid());
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestBody UpdateProfileDto profileDto, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        profileService.updateProfile(profileDto, authorizedUser.getUuid());
    }

    @GetMapping
    public ProfileInfoDto getProfileOfCurrentUser(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return profileService.getProfile(authorizedUser.getUuid());
    }

    @PostMapping("/avatar")
    public StatusDto uploadImage(@RequestParam("picture") MultipartFile picture, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.uploadObject(picture, authorizedUser.getUuid());
    }

    @GetMapping("/avatar")
    public PictureDto getImageUrl(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return minioStorageService.getPictureInfo(authorizedUser.getUuid());
    }

}
