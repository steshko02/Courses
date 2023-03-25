package com.example.coursach.dto.user;

import com.example.coursach.dto.pagable.PagingDtoList;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserPagedDto {

    private final PagingDtoList paging;

    private final List<BaseUserInformationDto> users;

}
