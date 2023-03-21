package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class NotNullFieldValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.NOT_NULL;
    }

    @Override
    public void validate(ValidationRule rule, String field) {
        if (!StringUtils.hasText(field)) {
            throw new InvalidFieldException(rule.getErrorMessage());
        }
    }

}
