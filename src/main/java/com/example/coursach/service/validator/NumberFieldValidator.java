package com.example.coursach.service.validator;

import com.example.coursach.exception.user.InvalidFieldException;
import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NumberFieldValidator implements FieldValidator {
    private static final String NUMBER_REGEX = "(-?\\d+)";

    @Override
    public ValidationType getType() {
        return ValidationType.NUMBER;
    }

    @Override
    public void validate(ValidationRule rule, String field) {
        if (Objects.isNull(field)) {
            return;
        }
        Pattern pattern = Pattern.compile(NUMBER_REGEX);
        Matcher matcher = pattern.matcher(field);
        if (!matcher.matches()) {
            throw new InvalidFieldException(rule.getErrorMessage());
        }
    }
}
