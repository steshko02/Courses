package com.example.coursach.config.properties;

import com.example.coursach.entity.enums.ItemType;
import eu.senla.git.coowning.entity.enums.FieldType;
import eu.senla.git.coowning.entity.enums.ItemType;
import eu.senla.git.coowning.entity.enums.Step;
import eu.senla.git.coowning.service.validator.model.ValidationRule;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Validated
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "app")
public class ItemTypeProperties {

    @NotEmpty
    private final List<ItemProperty> displayedNames;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ItemProperty {

        @NotNull
        private final ItemType id;

        @NotNull
        private final String name;

    }
}


