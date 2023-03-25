package com.example.coursach.config.properties.minio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Validated
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "app.minio.storage")
public class MinioDataStorageProperties {

    @NotBlank
    private final String defaultUserAvatar;

    @NotBlank
    private final String userAvatarsBucket;

    @NotBlank
    private final String redirectionEndPoint;

    @NotBlank
    private final String avatarsDirectoryPath;

    @NotBlank
    private final String invoicesDirectoryPath;

    @NotEmpty
    private final String[] allowedPhotoFormats;

    @NotBlank
    private final String serverStorageDirectory;

    @NotEmpty
    private final String coursesSenlaDefaultAvatar;

    @Min(value = 0)
    private final Integer coursesSenlaPicturesLimit;

    @NotEmpty
    private final String itemsPicturesStorageBucket;

}
