package com.example.coursach.security.utils;

import com.example.coursach.entity.Role;
import com.example.coursach.entity.User;
import com.example.coursach.entity.enums.UserRole;
import com.example.coursach.security.model.AuthorizedUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDetailsFactory {

    private static final String ROLE_PREFIX = "ROLE_";

    public AuthorizedUser create(User user) {
        return new AuthorizedUser(
                user.getEmail(),
                user.getPassword(),
                user.getId(),
                mapRoleToGrantedAuthorities(user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
        );
    }

    private List<SimpleGrantedAuthority> mapRoleToGrantedAuthorities(Set<UserRole> role) {
        return role.stream().map(r->new SimpleGrantedAuthority(ROLE_PREFIX + r.toString())).toList();
    }

}
