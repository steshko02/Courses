package com.example.coursach.config.properties.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Validated
@ConstructorBinding
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.minio.server")
public class MinioServerConfigurationProperties {

    @NotBlank
    private final String endPoint;

    @NotBlank
    private final String accessKey;

    @NotBlank
    private final String secretKey;

    @NotBlank
    private final String signerOverride;

    @NotBlank
    private final String maximumFileSize;

    @PositiveOrZero
    private final Integer connectionTimeout;

    @PositiveOrZero
    private final Integer requestTimeout;

}
