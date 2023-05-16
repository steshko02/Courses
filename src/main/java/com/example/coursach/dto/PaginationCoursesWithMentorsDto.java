package com.example.coursach.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class PaginationCoursesWithMentorsDto {

    private Long totalCount;
    private Integer totalPages;
    private Integer currentPage;
    private List<CourseDtoForMentors> courses;
}