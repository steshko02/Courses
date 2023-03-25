package com.example.coursach.storage.extractor;

import com.example.coursach.exception.other.EmptyFileException;
import com.example.coursach.exception.photo.WrongPhotoFormatException;
import com.example.coursach.storage.pattern.Patterns;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@UtilityClass
public class Extractors {

    /**
     * @param multipartFile              MultipartFile got from request params
     * @param finalFileName              like {itemUuid}.{dataFormat}
     * @param serverStorageDirectoryPath directory where pictures store at the server
     * @return java.io.File
     */
    public File extractFileFromMultipart(MultipartFile multipartFile, String finalFileName,
                                         String serverStorageDirectoryPath) {
        Path pathTo = Path.of(serverStorageDirectoryPath);
        if (!Files.exists(pathTo)) {
            try {
                Files.createDirectory(pathTo);
            } catch (IOException e) {
                throw new EmptyFileException();
            }
        }

        if (!multipartFile.isEmpty()) {
            try {
                File file = new File(serverStorageDirectoryPath + finalFileName);
                Files.copy(multipartFile.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return file;
            } catch (IOException ex) {
                throw new EmptyFileException(multipartFile.getName());
            }
        } else {
            throw new EmptyFileException(multipartFile.getName());
        }
    }

    /**
     * @param multipartFile      MultipartFile got from request params
     * @param itemUuid           item uuid
     * @param allowedPhotoFormat allowed pictures format from MinioDataStorage.class
     * @return filename like {itemUuid}.{dataFormat}
     */
    public String extractFileName(MultipartFile multipartFile, String itemUuid,
                                  @NotEmpty String[] allowedPhotoFormat) {
        String format = extractPictureFormat(multipartFile.getOriginalFilename());

        if (Arrays.stream(allowedPhotoFormat)
                .noneMatch(allowedFormat -> allowedFormat.equals(format))) {
            throw new WrongPhotoFormatException();
        }

        return String.format(Patterns.PHOTO_FORMAT_PATTERN, itemUuid, format);
    }

    /**
     * @param filename filename from Multipart (ex.: file.format)
     * @return format (from example upper)
     */
    public String extractPictureFormat(String filename) {
        String[] temp = Objects.requireNonNull(filename).split("\\.");

        if (temp.length == 0) {
            throw new WrongPhotoFormatException();
        }

        return temp[temp.length - 1];
    }

    /**
     * @param redirectionEndPoint from MinioDataStorage.class
     * @param bucketName          from MinioDataStorage.class
     * @param source              {itemUuid}.{dataFormat}
     * @return Minio url to get picture.
     */
    public String constructSignedUrl(String redirectionEndPoint, String bucketName, String source) {

        return String.format(Patterns.LINK_FORMAT_PATTERN, redirectionEndPoint,
                bucketName, source);
    }

}
