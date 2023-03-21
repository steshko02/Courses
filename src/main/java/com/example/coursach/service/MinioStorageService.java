package com.example.coursach.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import eu.senla.git.coowning.config.properties.minio.MinioDataStorageProperties;
import eu.senla.git.coowning.dto.picture.PictureDto;
import eu.senla.git.coowning.dto.picture.StatusDto;
import eu.senla.git.coowning.entity.Invoice;
import eu.senla.git.coowning.entity.Profile;
import eu.senla.git.coowning.entity.SharedItem;
import eu.senla.git.coowning.entity.User;
import eu.senla.git.coowning.exception.photo.PictureDontExistsException;
import eu.senla.git.coowning.exception.photo.WrongPhotoFormatException;
import eu.senla.git.coowning.exception.profile.ProfileNotFoundException;
import eu.senla.git.coowning.exception.shareditem.SharedItemsLimitException;
import eu.senla.git.coowning.exception.user.UserNotFoundException;
import eu.senla.git.coowning.repository.InvoiceRepository;
import eu.senla.git.coowning.repository.ProfileRepository;
import eu.senla.git.coowning.repository.SharedItemsRepository;
import eu.senla.git.coowning.repository.UserRepository;
import eu.senla.git.coowning.storage.extractor.Extractors;
import eu.senla.git.coowning.storage.pattern.Patterns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    private final InvoiceRepository invoiceRepository;
    private final SharedItemsRepository sharedItemsRepository;

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

    public StatusDto uploadObject(MultipartFile photoToUpload, Invoice invoice, String itemUuid) {

        String invoiceUuid = invoice.getUuid();
        String filename = Extractors.extractFileName(photoToUpload,
                invoiceUuid,
                minioProperties.getAllowedPhotoFormats());

        File finalFile = Extractors.extractFileFromMultipart(photoToUpload, filename,
                String.format(Patterns.SERVER_STORAGE_PATTERN, minioProperties.getInvoicesDirectoryPath(), itemUuid));

        Optional<String> pictureFormat = Optional.ofNullable(invoice.getPictureFormat());
        pictureFormat.ifPresent(p -> amazonS3Client
                .deleteObject(String.format("%s/%s", minioProperties.getInvoicesPictureStorageBucket(), itemUuid),
                        String.format(Patterns.PHOTO_FORMAT_PATTERN, invoiceUuid, pictureFormat.get())));

        amazonS3Client.putObject(new PutObjectRequest(minioProperties.getInvoicesPictureStorageBucket(),
                String.format(Patterns.MINIO_FOLDER_PATTERN, itemUuid, filename), finalFile));

        invoice.setPictureFormat(Extractors.extractPictureFormat(finalFile.getName()));
        invoiceRepository.save(invoice);

        finalFile.delete();
        return StatusDto.builder()
                .pictureId(invoiceUuid)
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

            if (pictureNumber > minioProperties.getSharedItemPicturesLimit()) {
                throw new SharedItemsLimitException();
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

    public String removeObject(SharedItem sharedItem, Integer pictureId) {
        final String sharedItemFolderLink = constructMinioFolderLink(sharedItem, pictureId);

        if (amazonS3Client.doesObjectExist(minioProperties.getItemsPicturesStorageBucket(), sharedItemFolderLink)) {
            amazonS3Client.deleteObject(minioProperties.getItemsPicturesStorageBucket(), sharedItemFolderLink);

            return getPictureWithFormat(sharedItem.getPictures(), pictureId);
        } else {
            throw new PictureDontExistsException();
        }
    }

    private String constructMinioFolderLink(SharedItem sharedItem, Integer pictureId) {
        return String.format(Patterns.MINIO_FOLDER_PATTERN, sharedItem.getUuid(),
                getPictureWithFormat(sharedItem.getPictures(), pictureId));
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

    private String getUserAvatarFinalPictureName(String userUuid, String format) {
        if (Objects.isNull(format)) {
            return minioProperties.getDefaultUserAvatar();
        }

        return String.format(Patterns.PHOTO_FORMAT_PATTERN, userUuid, format);
    }

    public String constructSharedItemMainPictureUrl(SharedItem sharedItem) {
        String temp;
        if (CollectionUtils.isEmpty(sharedItem.getPictures())) {
            temp = minioProperties.getSharedItemDefaultAvatar();
        } else {
            temp = sharedItem.getPictures().get(0);
        }

        return Extractors.constructSignedUrl(
                minioProperties.getRedirectionEndPoint(),
                minioProperties.getItemsPicturesStorageBucket(),
                String.format(Patterns.MINIO_FOLDER_PATTERN, sharedItem.getUuid(), temp)
        );
    }

    public String constructInvoicePictureUrl(Invoice invoice) {
        return Extractors.constructSignedUrl(
                minioProperties.getRedirectionEndPoint(),
                minioProperties.getInvoicesPictureStorageBucket(),
                String.format(Patterns.MINIO_FOLDER_PATTERN,
                        invoice.getItem().getUuid(),
                        String.format(Patterns.PHOTO_FORMAT_PATTERN, invoice.getUuid(), invoice.getPictureFormat())
                )
        );
    }

}
