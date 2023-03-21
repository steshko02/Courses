package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class MinFieldValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.MIN;
    }

    @Override
    public void validate(ValidationRule rule, String str) {
        if (Objects.nonNull(str) && str.length() < Integer.parseInt(rule.getMin())) {
            throw new InvalidFieldException(rule.getErrorMessage(), str);
        }
    }

}
