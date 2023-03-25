package com.example.coursach.service.converter.resolvers;

import com.example.coursach.dto.PresetFieldDto;

import java.util.List;

@FunctionalInterface
public interface PresetFieldsResolver {

    List<PresetFieldDto> getFieldsByItemUuid(String uuid);

}
