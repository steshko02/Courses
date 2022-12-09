package com.example.coursach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class ConfirmUserDto {

    private Integer code;

    private String email;
}
