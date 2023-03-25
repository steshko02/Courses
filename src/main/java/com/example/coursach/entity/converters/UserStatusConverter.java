package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.AccountStatus;

import javax.persistence.AttributeConverter;

public class UserStatusConverter implements AttributeConverter<AccountStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountStatus userStatus) {
        return userStatus.getVal();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer integer) {
        return AccountStatus.lookup(integer);
    }
}
