package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.AccountStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AccountStatusConverter implements AttributeConverter<AccountStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountStatus status) {
        return status.getVal();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer statusVal) {
        return AccountStatus.lookup(statusVal);
    }

}
