package com.example.coursach.service.converter.resolvers;

import java.util.List;

@FunctionalInterface
public interface PresetFieldsResolver {

    List<PresetFieldDto> getFieldsByItemUuid(String uuid);

}
