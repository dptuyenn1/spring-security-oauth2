package com.dev.annotations;

import com.dev.validators.StringsInCollectionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StringsInCollectionValidator.class)
public @interface StringsInCollection {

    String[] values();

    String message() default "must be any of {values}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}