package com.mts.teta.courses.handler.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TitleCaseConstraintValidator.class)
public @interface TitleCase {
    TitleCaseValue titleCaseValue() default TitleCaseValue.ANY;

    String message() default "Title not Correct";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
