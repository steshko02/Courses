package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MaxValueValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.MAX_VALUE;
    }

    @Override
    public void validate(ValidationRule rule, String field) {
        if (Objects.isNull(field)) {
            return;
        }
        try {
            if (Integer.parseInt(field) > Integer.parseInt(rule.getMax())) {
                throw new InvalidFieldException(rule.getErrorMessage(), field);
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(rule.getErrorMessage(), field);
        }
    }
}
