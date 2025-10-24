package com.dev.validators;

import com.dev.annotations.Enum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private List<String> values;
    private String message;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.values = Arrays
                .stream(constraintAnnotation.enumClazz().getEnumConstants())
                .map(java.lang.Enum::name)
                .toList();
        this.message = "Value must be in " + values;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

        return StringUtils.isBlank(value) || values.contains(value);
    }
}
