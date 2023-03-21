package com.example.coursach.dto.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

@Data
@Jacksonized
@Builder
@AllArgsConstructor
public class ErrorMessageDto {

    @JsonIgnore
    private final HttpStatus status;

    private final String code;

    private final String message;
}
