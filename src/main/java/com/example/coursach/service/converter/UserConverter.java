package com.example.coursach.service.converter;

import com.example.coursach.config.properties.UserProperties;
import com.example.coursach.dto.pagable.PagingDtoList;
import com.example.coursach.dto.security.RegisterConfirmDto;
import com.example.coursach.dto.security.RegisterRequestDto;
import com.example.coursach.dto.user.BaseUserInformationDto;
import com.example.coursach.dto.user.UserPagedDto;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.Role;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.AccountStatus;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.service.converter.resolvers.UserPictureUrlResolver;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class UserConverter {

    private final UserProperties userProperties;

    public UserConverter(UserProperties userProperties) {
        this.userProperties = userProperties;
    }

    public User updatePassword(RegisterConfirmDto requestDto, User user, PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return user;
    }

    public User toEntity(RegisterRequestDto requestDto, PasswordEncoder passwordEncoder, UserRole userRole) {
        String password = requestDto.getPassword();
        return User.builder()
                .roles(Set.of(Role.builder().name(userRole).build()))
                .accountStatus(null)
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(password == null ? "" : password))
                .build();
    }

    public BaseUserInformationDto userToBaseUserInformationDto(User user, UserPictureUrlResolver urlMapperFunction) {
        Profile profile = ProfileConverter.getProfileOrEmptyProfile(user);
        return BaseUserInformationDto.builder()
                .uuid(user.getId())
                .email(user.getEmail())
                .nickname(profile.getNickname())
                .avatarUrl(urlMapperFunction.getPictureUrlByUser(user))
                .build();
    }

    public List<BaseUserInformationDto> listUserToListBaseUserInformationDto(
            List<User> userList, UserPictureUrlResolver urlMapperFunction) {
        if (CollectionUtils.isEmpty(userList)) {
            return Collections.emptyList();
        }
        return userList.stream()
                .map(user -> userToBaseUserInformationDto(user, urlMapperFunction))
                .collect(Collectors.toList());
    }

    public UserPagedDto userPagedResultToUserPagedDto(Page<User> pagedResult, UserPictureUrlResolver urlMapperFunction) {
        List<User> userList = pagedResult.getContent();
        return UserPagedDto
                .builder()
                .paging(PagingDtoList
                        .builder()
                        .totalPages(pagedResult.getTotalPages())
                        .totalCount((int) pagedResult.getTotalElements())
                        .pageNumber(pagedResult.getNumber() + 1)
                        .build())
                .users(listUserToListBaseUserInformationDto(userList, urlMapperFunction)).build();
    }

}
