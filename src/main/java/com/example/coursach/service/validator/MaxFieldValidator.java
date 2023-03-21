package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MaxFieldValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.MAX;
    }

    @Override
    public void validate(ValidationRule rule, String str) {
        if (Objects.nonNull(str) && str.length() > Integer.parseInt(rule.getMax())) {
            throw new InvalidFieldException(rule.getErrorMessage(), str);
        }
    }

}
