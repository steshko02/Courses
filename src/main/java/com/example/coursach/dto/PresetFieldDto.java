package com.example.coursach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class PresetFieldDto {

    private final String id;

    private final String title;

    private final String value;

}
