package com.example.coursach.service;

import com.example.coursach.converters.UserConverter;
import com.example.coursach.dto.ConfirmUserDto;
import com.example.coursach.dto.RegistrationUserDto;
import com.example.coursach.entity.Code;
import com.example.coursach.entity.Role;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.CodeType;
import com.example.coursach.entity.enums.UserRole;
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

    public Long registrationUser(RegistrationUserDto registrationUserDto){

        User save1 = userConverter.toEntityWithoutRole(registrationUserDto);
        save1.getRoles().add(Role.builder().name(UserRole.USER).build());

        save1 = userRepository.save(save1);

        otpRepository.save(codeCreator.create(save1));
        return save1.getId();
    }

    public void confirmUser(ConfirmUserDto confirmUserDto){
        Code byCodeAndRequestedEmail = otpRepository
                .findByCodeAndRequestedCredentialEmailAndType(confirmUserDto.getCode(), confirmUserDto.getEmail(), CodeType.AUTH);

        User requested = byCodeAndRequestedEmail.getRequested();
        requested.setStatus(UserStatus.ACTIVE);
        userRepository.save(requested);
        otpRepository.delete(byCodeAndRequestedEmail);
    }
}
