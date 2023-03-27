package com.example.coursach.dto.file;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@Jacksonized
public class FileUploadMetaRequestDto {

    @NotEmpty
    private String extension;

    @NotEmpty
    private String lessonId;

    @NotEmpty
    private String courseId;

}
