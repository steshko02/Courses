package com.example.coursach.dto.user;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserDto {

    @NotNull
    private final String id;

    @NotNull
    private final String firstName;

    private final String lastName;

}
