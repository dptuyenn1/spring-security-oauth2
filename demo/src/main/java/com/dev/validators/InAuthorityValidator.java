package com.dev.validators;

import com.dev.annotations.InAuthority;
import com.dev.enums.Authority;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InAuthorityValidator implements ConstraintValidator<InAuthority, Set<String>> {

    private static final String[] AUTHORITIES =
            Arrays.stream(Authority.values()).map(Enum::name).toArray(String[]::new);
    private static final String MESSAGE = "must be in " + Arrays.toString(AUTHORITIES);

    @Override
    public boolean isValid(Set<String> inputs, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(MESSAGE).addConstraintViolation();

        if (inputs.size() == 1)
            return Arrays.stream(AUTHORITIES).anyMatch(authority -> authority.equals(inputs.iterator().next()));

        return inputs.isEmpty() || new HashSet<>(List.of(AUTHORITIES)).containsAll(inputs);
    }
}
