package com.example.coursach.dto.pagable;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class PagingDtoList {

    private final Integer totalPages;

    private final Integer totalCount;

    private final Integer pageNumber;

}
