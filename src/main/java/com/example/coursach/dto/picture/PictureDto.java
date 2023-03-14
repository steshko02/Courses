package com.example.coursach.dto.picture;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PictureDto {

    private final String url;

    private final String belongTo;

}
