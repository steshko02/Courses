package com.example.coursach.dto.picture;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class StatusDto {

    private final String message;

    private final String pictureId;

    private final String timestamp;

}
