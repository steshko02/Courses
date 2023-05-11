package com.example.coursach.service;

import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.security.utils.UserDetailsFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsFactory userDetailsFactory;

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserDetailsFactory userDetailsFactory, UserRepository userRepository) {
        this.userDetailsFactory = userDetailsFactory;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findUserByEmail(username)
                .map(userDetailsFactory::create)
                .orElseThrow(UserNotFoundException::new);
    }
}
