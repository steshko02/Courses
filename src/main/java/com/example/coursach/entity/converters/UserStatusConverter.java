package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.UserStatus;

import javax.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<UserStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserStatus userStatus) {
        return userStatus.getVal();
    }

    @Override
    public UserStatus convertToEntityAttribute(Integer integer) {
        return UserStatus.lookup(integer);
    }
}
