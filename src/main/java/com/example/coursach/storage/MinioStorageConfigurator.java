package com.example.coursach.storage;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.services.s3.AmazonS3;
import com.example.coursach.config.properties.minio.MinioDataStorageProperties;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;


@Component
public class MinioStorageConfigurator {

    private final ResourceLoader resourceLoader;

    private final AmazonS3 amazonS3Client;

    private final MinioDataStorageProperties minioProperties;

    public MinioStorageConfigurator(ResourceLoader resourceLoader, AmazonS3 amazonS3Client,
                                    MinioDataStorageProperties minioProperties) {
        this.resourceLoader = resourceLoader;
        this.amazonS3Client = amazonS3Client;
        this.minioProperties = minioProperties;
    }

    @SneakyThrows
    @PostConstruct
    public void storageInitialize() {
        if (!amazonS3Client.doesBucketExistV2(minioProperties.getUserAvatarsBucket())) {
            amazonS3Client.createBucket(minioProperties.getUserAvatarsBucket());
            amazonS3Client.setBucketPolicy(minioProperties.getUserAvatarsBucket(),
                    getPublicReadPolicy(minioProperties.getUserAvatarsBucket()));
        }

        if (!amazonS3Client.doesBucketExistV2(minioProperties.getItemsPicturesStorageBucket())) {
            amazonS3Client.createBucket(minioProperties.getItemsPicturesStorageBucket());
            amazonS3Client.setBucketPolicy(minioProperties.getItemsPicturesStorageBucket(),
                    getPublicReadPolicy(minioProperties.getItemsPicturesStorageBucket()));
        }

        String courses = "courses";
        if (!amazonS3Client.doesBucketExistV2(courses)) {
            amazonS3Client.createBucket(courses);
            amazonS3Client.setBucketPolicy(courses, getPublicReadPolicy(courses));
        }

        String lesson = "lesson";
        if (!amazonS3Client.doesBucketExistV2(lesson)) {
            amazonS3Client.createBucket(lesson);
            amazonS3Client.setBucketPolicy(lesson, getPublicReadPolicy(lesson));
        }

        String answer = "answer";
        if (!amazonS3Client.doesBucketExistV2(answer)) {
            amazonS3Client.createBucket(answer);
            amazonS3Client.setBucketPolicy(answer, getPublicReadPolicy(answer));
        }

        if (!Files.exists(Path.of(minioProperties.getServerStorageDirectory()))) {
            Files.createDirectory(Path.of(minioProperties.getServerStorageDirectory()));
        }
    }

    private String getPublicReadPolicy(String bucketName) {
        Policy bucketPolicy = new Policy().withStatements(
                new Statement(Statement.Effect.Allow)
                        .withPrincipals(Principal.AllUsers)
                        .withActions(S3Actions.GetObject)
                        .withResources(new Resource(
                                String.format("arn:aws:s3:::%s/*", bucketName))));
        return bucketPolicy.toJson();
    }

}
