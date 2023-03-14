package com.example.coursach.security.utils;

import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.security.model.AuthorizedUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsFactory {

    private static final String ROLE_PREFIX = "ROLE_";

    public AuthorizedUser create(User user) {
        return new AuthorizedUser(
                user.getEmail(),
                user.getPassword(),
                user.getId(),
                mapRoleToGrantedAuthorities(user.getRole())
        );
    }

    private List<GrantedAuthority> mapRoleToGrantedAuthorities(UserRole role) {
        return List.of(
                new SimpleGrantedAuthority(ROLE_PREFIX + role.toString())
        );
    }

}
