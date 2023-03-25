package com.example.coursach.storage.pattern;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Patterns {

    /**
     * Full name pattern<br/>
     * Usage example: used for construct full name
     */
    public static final String FULL_NAME_PATTERN = "%s %s";

    public static final String PICTURE_SPLIT_PATTERN = "\\.";

    /**
     * Photo format pattern<br/>
     * Usage example: used for construct file name to file.format (user.jpg)
     */
    public static final String PHOTO_FORMAT_PATTERN = "%s.%s";

    /**
     * Minio folder pattern
     * Usage example: used for construct url with folder
     */
    public static final String MINIO_FOLDER_PATTERN = "%s/%s";

    /**
     * Link format pattert<br/>
     * Usage example: used for construct url like http://host:port/bucket/file.jpg
     */
    public static final String LINK_FORMAT_PATTERN = "%s/%s/%s";

    /**
     * Server storage directory pattert<br/>
     * Usage example: used for construct storage directory like ../photos/uuid/
     */
    public static final String SERVER_STORAGE_PATTERN = "%s%s/";

    public static final String SERVER_FILE_STORAGE = "%s%s.txt";

    public static final String COURSES_NAMING_PATTERN = "COURSES %s";
}
