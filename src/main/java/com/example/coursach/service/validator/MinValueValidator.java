package com.example.coursach.service.validator;

import com.example.coursach.exception.user.InvalidFieldException;
import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MinValueValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.MIN_VALUE;
    }

    @Override
    public void validate(ValidationRule rule, String field) {
        if (Objects.isNull(field)) {
            return;
        }
        try {
            if (Integer.parseInt(field) < Integer.parseInt(rule.getMin())) {
                throw new InvalidFieldException(rule.getErrorMessage(), field);
            }
        } catch (NumberFormatException e) {
            throw new InvalidFieldException(rule.getErrorMessage(), field);
        }
    }
}
