package com.example.coursach.service;

import com.example.coursach.config.properties.ItemTypeProperties;
import com.example.coursach.entity.enums.ItemType;
import com.example.coursach.exception.ErrorCode;
import com.example.coursach.service.validator.FieldValidator;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdditionalFieldsValidationService {

    private final ItemTypeProperties itemTypeProperties;

    private final Map<ValidationType, FieldValidator> validators;

    public AdditionalFieldsValidationService(ItemTypeProperties itemTypeProperties, Map<ValidationType, FieldValidator> validators) {
        this.itemTypeProperties = itemTypeProperties;
        this.validators = validators;
    }

//
//    public void rulesValidation(List<String> fields) {
//        if (fields.isEmpty()) {
//            return;
//        }
//        fields.forEach(value -> {
//            if (!StringUtils.hasText(value) || value.length() > 200 || value.length() < 2) {
//                throw new InvalidFieldException(ErrorCode.WEAK_RULES.getCode(), "rules");
//            }
//        });
//    }
//
//    public Map<String, String> checkAndReturnWithNewFields(Map<String, String> fields, ItemType itemType) {
//        Map<String, String> newFields = new LinkedHashMap<>();
//
//        Map<String, ItemTypeProperties.ItemField> requiredFieldMap = itemTypeProperties.getRequiredFieldMap(itemType);
//
//        for (ItemTypeProperties.ItemField item : requiredFieldMap.values()) {
//
//            String value = fields.get(item.getId());
//            newFields.put(
//                    item.getId(),
//                    value
//            );
//        }
//
//        return newFields;
//    }
//
//    public void validateCostFields(Map<String, Integer> fields) {
//        if (MapUtils.isEmpty(fields)) {
//            return;
//        }
//        fields.forEach((key, value) -> {
//            if (!StringUtils.hasText(key) || key.length() > 50 || key.length() < 2) {
//                throw new InvalidFieldException(ErrorCode.WEAK_COST.getCode(), key);
//            }
//            if (value == null || value < 1) {
//                throw new InvalidFieldException(ErrorCode.WEAK_COST.getCode(), key);
//            }
//        });
//    }
//
//    public void validateAdditionalFields(Map<String, String> fields) {
//        if (MapUtils.isEmpty(fields)) {
//            return;
//        }
//
//        fields.forEach((key, value) -> {
//
//            if (!StringUtils.hasText(key) || key.length() > 50 || key.length() < 2) {
//                throw new InvalidFieldException(ErrorCode.INVALID_ADDITIONAL_FIELD.getCode(), key);
//            }
//
//            if (!StringUtils.hasText(value) || value.length() > 50 || value.length() < 2) {
//                throw new InvalidFieldException(ErrorCode.INVALID_ADDITIONAL_FIELD.getCode(), key);
//            }
//
//        });
//    }

}
