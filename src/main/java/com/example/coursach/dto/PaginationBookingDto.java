package com.example.coursach.dto;

import com.example.coursach.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class PaginationBookingDto {

    private Long totalCount;
    private Integer totalPages;
    private Integer currentPage;
    private List<BookingDto> bookings;
}