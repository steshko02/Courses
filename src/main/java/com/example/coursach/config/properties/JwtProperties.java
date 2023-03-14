package com.example.coursach.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.util.Base64;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotBlank
    private final String secretWord;

    @NotBlank
    private final String hours;

    @NotBlank
    private final String prefix;

    @NotBlank
    private final String accessTokenKey;

    @Null
    private String secret;

    @PostConstruct
    protected void init() {
        this.secret = Base64.getEncoder().encodeToString(secretWord.getBytes());
    }

}
