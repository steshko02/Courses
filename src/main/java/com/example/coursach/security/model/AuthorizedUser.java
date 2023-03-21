package com.example.coursach.security.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthorizedUser extends User {

    private final String uuid;

    public AuthorizedUser(String username, String password, String uuid, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.uuid = uuid;
    }
}
