package com.example.coursach.storage;

import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.Statement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Policy;
import java.security.Principal;

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
            /*Uncomment when UX/UI will provide default user avatar.
            amazonS3Client.putObject(new PutObjectRequest(minioProperties.getUserAvatarsBucket(),
                    minioProperties.getDefaultUserAvatar(), getDefaultAvatarFileObject(String.format("%s%s",
                    minioProperties.getServerStorageDirectory(), minioProperties.getDefaultUserAvatar()))));*/
        }

        if (!amazonS3Client.doesBucketExistV2(minioProperties.getItemsPicturesStorageBucket())) {
            amazonS3Client.createBucket(minioProperties.getItemsPicturesStorageBucket());
            amazonS3Client.setBucketPolicy(minioProperties.getItemsPicturesStorageBucket(),
                    getPublicReadPolicy(minioProperties.getItemsPicturesStorageBucket()));
        }

        if (!amazonS3Client.doesBucketExistV2(minioProperties.getInvoicesPictureStorageBucket())) {
            amazonS3Client.createBucket(minioProperties.getInvoicesPictureStorageBucket());
            amazonS3Client.setBucketPolicy(minioProperties.getInvoicesPictureStorageBucket(),
                    getPublicReadPolicy(minioProperties.getInvoicesPictureStorageBucket()));
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
    /*Unused method. Problem solve when UX/UI will provide default user avatar.
    private File getDefaultAvatarFileObject(String path) throws IOException {

        return resourceLoader.getResource(String.format("classpath:%s", path)).getFile();
    }*/
}
