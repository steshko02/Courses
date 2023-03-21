package com.example.coursach.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "regex")
public class RegexProperties {

    @NotBlank
    private final String password;

}
