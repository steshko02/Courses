package com.example.coursach.service.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.example.coursach.config.properties.minio.MinioDataStorageProperties;
import com.example.coursach.config.properties.minio.MinioServerConfigurationProperties;
import com.example.coursach.dto.file.FileUploadMetaRequestDto;
import com.example.coursach.storage.pattern.Patterns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.example.coursach.storage.pattern.Patterns.MINIO_FOLDER_PATTERN;
import static com.example.coursach.storage.pattern.Patterns.MINIO_FOLDER_PATTERN_WITH_FILENAME;
import static com.example.coursach.storage.pattern.Patterns.PHOTO_FORMAT_PATTERN;

@Service
@RequiredArgsConstructor
public class FileLoaderService {

    private final AmazonS3 s3Client;

    private final MinioDataStorageProperties minioProperties;

    public static final String MINIO_FOLDER_PATTERN_FOR_LESSON = "%s/%s/%s/%s.%s"; // /courseid/lessonid/video/id

    public String createUploadUrl(FileUploadMetaRequestDto requestDto) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE,5);

        return s3Client.generatePresignedUrl(
                "lesson" ,
                createFilePath(requestDto.getCourseId(),requestDto.getLessonId(),"video", requestDto.getExtension()),
                calendar.getTime(), HttpMethod.PUT).toString();
    }

    private String createFilePath(@NotEmpty Long course, @NotEmpty Long lessname, @NotEmpty String filename, String extension){
        return String.format(MINIO_FOLDER_PATTERN_FOR_LESSON, course, lessname, "videos", filename, extension);
    }
}
