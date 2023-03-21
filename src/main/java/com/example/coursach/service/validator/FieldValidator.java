package com.example.coursach.service.validator;

import com.example.coursach.service.validator.model.ValidationRule;
import com.example.coursach.service.validator.model.ValidationType;

public interface FieldValidator {

    ValidationType getType();

    void validate(ValidationRule rule, String field);

}
