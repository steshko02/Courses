package com.example.coursach.dto;

import com.example.coursach.entity.enums.ResourceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class ResourceDto {

    private Long id;

    private String extension;

    private String filename;

    private String url;

    private ResourceType type ;
}
