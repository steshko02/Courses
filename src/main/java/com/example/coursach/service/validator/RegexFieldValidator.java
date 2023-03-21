package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexFieldValidator implements FieldValidator {

    @Override
    public ValidationType getType() {
        return ValidationType.REGEXP;
    }
    @Override
    public void validate(ValidationRule rule, String str) {
        if (Objects.isNull(str)) {
            return;
        }
        Pattern pattern = Pattern.compile(rule.getRegexp());
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            throw new InvalidFieldException(rule.getErrorMessage());
        }
    }

}
