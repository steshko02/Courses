package com.example.coursach.entity.converters;

import com.example.coursach.entity.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserRole userRole) {
        return userRole.getVal();
    }

    @Override
    public UserRole convertToEntityAttribute(Integer roleVal) {
        return UserRole.lookup(roleVal);
    }

}
