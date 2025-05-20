package com.dev.validators;

import com.dev.annotations.StringsInCollection;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StringsInCollectionValidator implements ConstraintValidator<StringsInCollection, Collection<String>> {

    private String[] values;

    @Override
    public void initialize(StringsInCollection constraint) {
        this.values = constraint.values();
    }

    @Override
    public boolean isValid(Collection<String> inputs, ConstraintValidatorContext context) {
        if (inputs.size() == 1)
            return Arrays.stream(values).anyMatch(value -> value.equals(inputs.iterator().next()));

        return inputs.isEmpty() || inputs.containsAll(List.of(values));
    }
}
