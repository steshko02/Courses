package com.example.coursach.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.coursach.config.properties.minio.MinioDataStorageProperties;
import com.example.coursach.dto.picture.PictureDto;
import com.example.coursach.dto.picture.StatusDto;
import com.example.coursach.entity.Answer;
import com.example.coursach.entity.Course;
import com.example.coursach.entity.Lesson;
import com.example.coursach.entity.Profile;
import com.example.coursach.entity.Resource;
import com.example.coursach.entity.User;
import com.example.coursach.entity.Work;
import com.example.coursach.entity.enums.ResourceType;
import com.example.coursach.exception.photo.WrongPhotoFormatException;
import com.example.coursach.exception.profile.ProfileNotFoundException;
import com.example.coursach.exception.user.UserNotFoundException;
import com.example.coursach.repository.AnswerRepository;
import com.example.coursach.repository.CourseRepository;
import com.example.coursach.repository.LessonRepository;
import com.example.coursach.repository.ProfileRepository;
import com.example.coursach.repository.ResourceRepository;
import com.example.coursach.repository.UserRepository;
import com.example.coursach.repository.WorkRepository;
import com.example.coursach.storage.extractor.Extractors;
import com.example.coursach.storage.pattern.Patterns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.io.FilenameUtils;

import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioStorageService {

    private static final String ONE = "1";
    private static final String PLUG = "plug";
    private static final String SUCCESS_UPLOAD = "Photo successfully uploaded.";
    private final Clock systemClock;
    private final AmazonS3 amazonS3Client;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final MinioDataStorageProperties minioProperties;
    private final CourseRepository courseRepository;
    private final ResourceRepository resourceRepository;
    private final WorkRepository workRepository;
    private final AnswerRepository answerRepository;


    public static final String MINIO_FOLDER_PATTERN_FOR_LESSON = "%s/%s/%s/%s"; // /courseid/lessonid/video/id

    public StatusDto uploadLessonObj(MultipartFile picture,Long courseId, Long id) {

        String pictureName = String.valueOf(id);

        String finalFilename = Extractors.extractFileName(picture, pictureName+"-"+ UUID.randomUUID().toString(), new String[]{"docx"}); //todo
        File fileToUpload = Extractors.extractFileFromMultipart(picture, finalFilename,
                minioProperties.getAvatarsDirectoryPath());

        amazonS3Client.putObject(new PutObjectRequest("lesson",
                createFilePath(courseId,id,"documents",finalFilename),
                fileToUpload));

        fileToUpload.delete();

        return StatusDto.builder()
                .pictureId(pictureName)
                .timestamp(LocalDateTime.now(systemClock).toString())
                .message(SUCCESS_UPLOAD)
                .build();
    }

    public StatusDto uploadAnswerObj(MultipartFile picture, String userUuid, Long answerId) {

        Answer byId = answerRepository.findById(answerId).orElseThrow(RuntimeException::new);
        
        String filenameDoc = String.valueOf(byId.getId());

        String finalFilename = Extractors.extractFileName(picture, filenameDoc+"-"+ UUID.randomUUID(), new String[]{"docx"}); //todo
        File fileToUpload = Extractors.extractFileFromMultipart(picture, finalFilename,
                minioProperties.getAvatarsDirectoryPath());

        String filePath = String.format("%s/%s/%s/%s", userUuid, byId.getWork().getId(), answerId, finalFilename);
        amazonS3Client.putObject(new PutObjectRequest("answer",
                filePath,
                fileToUpload));
        
        String extension = FilenameUtils.getExtension(picture.getOriginalFilename());

        fileToUpload.delete();

        Optional.of(byId).ifPresent(x-> {
                    x.getResources().add(Resource.builder()
                            .extension(extension)
                            .filename(picture.getOriginalFilename())
                            .url(getObjectUrl("answer", filePath, ""))
                            .type(ResourceType.DOCUMENT)
                            .build());
                    answerRepository.save(x);
                }
        );

        fileToUpload.delete();

        return StatusDto.builder()
                .pictureId(filenameDoc)
                .timestamp(LocalDateTime.now(systemClock).toString())
                .message(SUCCESS_UPLOAD)
                .build();
    }

    public StatusDto uploadWorkObj(MultipartFile picture,Long courseId, Long lessonId, Long workId) {

        String pictureName = String.valueOf(workId);

        String finalFilename = Extractors.extractFileName(picture, pictureName+"-"+ UUID.randomUUID(), new String[]{"docx"}); //todo
        File fileToUpload = Extractors.extractFileFromMultipart(picture, finalFilename,
                minioProperties.getAvatarsDirectoryPath());

        String filePath = createFilePath(courseId, lessonId, "works", finalFilename);
        amazonS3Client.putObject(new PutObjectRequest("lesson",
                filePath,
                fileToUpload));

        String extension = FilenameUtils.getExtension(picture.getOriginalFilename());

        fileToUpload.delete();

        Optional<Work> byId = workRepository.findById(workId);

        byId.ifPresent(x-> {
                    x.getResources().add(Resource.builder()
                            .extension(extension)
                            .filename(picture.getOriginalFilename())
                            .url(getObjectUrl("lesson", filePath, ""))
                            .type(ResourceType.DOCUMENT)
                            .build());
                    workRepository.save(x);
                }
        );

        fileToUpload.delete();

        return StatusDto.builder()
                .pictureId(pictureName)
                .timestamp(LocalDateTime.now(systemClock).toString())
                .message(SUCCESS_UPLOAD)
                .build();
    }
    private String createFilePath(@NotEmpty Long course, @NotEmpty Long lessname, String folderName, @NotEmpty String filename){
        return String.format(MINIO_FOLDER_PATTERN_FOR_LESSON, course, lessname, folderName, filename);
    }

    public StatusDto uploadObject(MultipartFile photoToUpload, String userUuid) {
        /*
         * TODO: SOLVE PROBLEM when bucket don`t exists
         * */

        String finalFilename = Extractors.extractFileName(photoToUpload, userUuid,
                minioProperties.getAllowedPhotoFormats());

        File fileToUpload = Extractors.extractFileFromMultipart(photoToUpload, finalFilename,
                minioProperties.getAvatarsDirectoryPath());

        Profile profile = profileRepository.findById(userUuid).orElseThrow(ProfileNotFoundException::new);

        Optional<String> pictureFormat = Optional.ofNullable(profile.getPictureFormat());

        pictureFormat.ifPresent(p -> amazonS3Client
                .deleteObject(minioProperties.getUserAvatarsBucket(),
                        String.format(Patterns.PHOTO_FORMAT_PATTERN, userUuid, pictureFormat.get())));

        amazonS3Client.putObject(new PutObjectRequest(minioProperties.getUserAvatarsBucket(),
                finalFilename, fileToUpload));

        User currentUser = userRepository.findUserByIdWithFetchProfile(userUuid)
                .orElseThrow(UserNotFoundException::new);
        currentUser.getProfile().setPictureFormat(Extractors.extractPictureFormat(photoToUpload.getOriginalFilename()));
        userRepository.save(currentUser);

        fileToUpload.delete();

        return StatusDto.builder()
                .pictureId(userUuid)
                .timestamp(LocalDateTime.now(systemClock).toString())
                .message(SUCCESS_UPLOAD)
                .build();
    }

    public List<String> uploadObject(String itemUuid, MultipartFile multipartFile, List<String> existingPictures) {
        Integer pictureNumber;

        if (CollectionUtils.isEmpty(existingPictures)) {
            pictureNumber = 1;
        } else {
            pictureNumber = Integer.parseInt(Arrays.stream(existingPictures
                    .get(existingPictures.size() - 1)
                    .split(Patterns.PICTURE_SPLIT_PATTERN)).findFirst().orElse(ONE)) + 1;

            if (pictureNumber > minioProperties.getCoursesSenlaPicturesLimit()) {
                throw new RuntimeException();
            }
        }

        String filename = Extractors.extractFileName(multipartFile,
                String.valueOf(pictureNumber),
                minioProperties.getAllowedPhotoFormats());

        File finalFile = Extractors.extractFileFromMultipart(multipartFile, filename,
                String.format(Patterns.SERVER_STORAGE_PATTERN, minioProperties.getAvatarsDirectoryPath(), itemUuid));

        amazonS3Client.putObject(new PutObjectRequest(minioProperties.getItemsPicturesStorageBucket(),
                String.format(Patterns.MINIO_FOLDER_PATTERN, itemUuid, filename), finalFile));

        finalFile.delete();
        existingPictures.add(filename);

        return existingPictures;
    }


    private String getPictureWithFormat(List<String> pictures, Integer pictureId) {
        if (CollectionUtils.isEmpty(pictures)) {
            return PLUG;
        }

        for (String item : pictures) {
            if (Integer.valueOf(item.split(Patterns.PICTURE_SPLIT_PATTERN)[0]).equals(pictureId)) {
                return item;
            }
        }

        throw new WrongPhotoFormatException();
    }

    public String getPictureUrl(User user) {
        return getPictureInfo(user).getUrl();
    }

    public PictureDto getPictureInfo(User user) {
        return PictureDto.builder()
                .belongTo(user.getId())
                .url(Extractors.constructSignedUrl(
                                minioProperties.getRedirectionEndPoint(),
                                minioProperties.getUserAvatarsBucket(),
                                getUserAvatarFinalPictureName(
                                        user.getId(),
                                        Optional.ofNullable(user.getProfile())
                                                .orElse(
                                                        Profile.builder().pictureFormat(user.getId()).build()
                                                ).getPictureFormat()
                                )
                        )
                )
                .build();
    }

    public PictureDto getPictureInfo(String userUuid) {
        return getPictureInfo(userRepository.findUserByIdWithFetchProfile(userUuid).orElseThrow(UserNotFoundException::new));
    }

    public PictureDto getCoursePictureInfo(String coursesId,String format) {
        return PictureDto.builder()
                .belongTo(coursesId)
                .url(Extractors.constructSignedUrl(
                                minioProperties.getRedirectionEndPoint(),
                                "courses",
                                coursesId + "."+format
                        )
                )
                .build();
    }

    public String getObjectUrl(String bucket, String source, String ext){
       return Extractors.constructSignedUrl(
                minioProperties.getRedirectionEndPoint(),
               bucket,
               ext.equals("")? source : source+ "."+ext
        );
    }

    private String getUserAvatarFinalPictureName(String userUuid, String format) {
        if (Objects.isNull(format)) {
            return minioProperties.getDefaultUserAvatar();
        }

        return String.format(Patterns.PHOTO_FORMAT_PATTERN, userUuid, format);
    }

    public StatusDto uploadCourseObj(MultipartFile picture, Long id) {

        String pictureName = String.valueOf(id);
        String finalFilename = Extractors.extractFileName(picture, pictureName, minioProperties.getAllowedPhotoFormats());
        File fileToUpload = Extractors.extractFileFromMultipart(picture, finalFilename,
                minioProperties.getAvatarsDirectoryPath());

        amazonS3Client.putObject(new PutObjectRequest("courses",
                finalFilename, fileToUpload));
        String extension = FilenameUtils.getExtension(picture.getOriginalFilename());

        PictureDto coursePictureInfo = getCoursePictureInfo(pictureName, extension);

        fileToUpload.delete();

        Resource resource = Resource.builder()
                .extension(extension)
                .filename(pictureName)
                .url(coursePictureInfo.getUrl())
                .type(ResourceType.PICTURE)
                .build();

        Course course = courseRepository.findById(id).get();
        course.setResources(resource);
        courseRepository.save(course);
        return StatusDto.builder()
                .pictureId(pictureName)
                .timestamp(LocalDateTime.now(systemClock).toString())
                .message(SUCCESS_UPLOAD)
                .build();
    }

}
