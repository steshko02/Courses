package com.example.coursach.service;

import com.example.coursach.converters.UserConverter;
import com.example.coursach.dto.ConfirmUserDto;
import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.entity.Code;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.UserStatus;
import com.example.coursach.repository.OtpRepository;
import com.example.coursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final UserConverter userConverter;

    private final CodeCreator codeCreator;

    public void registrationUser(RegistrationUserDto registrationUserDto){
        User save = userRepository.save(userConverter.toEntity(registrationUserDto));
        otpRepository.save(codeCreator.create(save));
    }

    public void confirmUser(ConfirmUserDto confirmUserDto){
        Code byCodeAndRequestedEmail = otpRepository
                .findByCodeAndRequestedCredentialEmail(confirmUserDto.getCode(), confirmUserDto.getEmail());

        User requested = byCodeAndRequestedEmail.getRequested();
        requested.setStatus(UserStatus.ACTIVE);
        userRepository.save(requested);
        otpRepository.delete(byCodeAndRequestedEmail);
    }
}
