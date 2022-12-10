package com.example.coursach.controllers;

import com.example.coursach.dto.ProfileUserDto;
import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("profiles")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void createProfile(@RequestBody ProfileUserDto profileUserDto) {
        profileService.createProfile(profileUserDto);
    }

    @PutMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfile(@RequestBody ProfileUserDto profileUserDto) {
        profileService.update(profileUserDto);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProfileUserDto get(@PathVariable("id") Long id) {
       return profileService.getById(id);
    }
}
