package com.example.coursach.service.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegistrationResult {
    private final String authenticationToken;
}
