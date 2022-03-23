package com.mts.teta.courses.handler.annotation;

import com.mts.teta.courses.handler.HandleTitleCaseValidate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleCaseConstraintValidator implements ConstraintValidator<TitleCase, String> {

    TitleCaseValue titleCaseValue;

    @Override
    public void initialize(TitleCase constraintAnnotation) {
        titleCaseValue = constraintAnnotation.titleCaseValue();
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {

        if (!HandleTitleCaseValidate.commonValidate(title)) {
            return false;
        }

        switch (titleCaseValue) {
            case EN:
                return HandleTitleCaseValidate.enValidate(title);
            case RU:
                return HandleTitleCaseValidate.ruValidate(title);
            case ANY:
                return HandleTitleCaseValidate.enValidate(title) || HandleTitleCaseValidate.ruValidate(title);
            default:
                return false;
        }
    }
}
