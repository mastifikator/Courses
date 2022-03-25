package com.mts.teta.courses.handler.annotation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TitleCaseConstraintValidatorTest {

    private static final String CORRECT_RU_TITLE = "Правильный русский заголовок";
    private static final String NOT_CORRECT_RU_TITLE = "не Правильный русский zagolovok";
    private static final String CORRECT_EN_TITLE = "Correct English the Title";
    private static final String NOT_CORRECT_EN_TITLE = "not Correct English the заголовок";

    private TitleCaseConstraintValidator titleCaseConstraintValidator;
    private TitleCase titleCase;

    @BeforeEach
    void prepareEnvironment() {
        titleCaseConstraintValidator = new TitleCaseConstraintValidator();
        titleCase = mock(TitleCase.class);
    }

    @Test
    void failRuValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.RU);

        titleCaseConstraintValidator.initialize(titleCase);

        assertFalse(titleCaseConstraintValidator.isValid(NOT_CORRECT_RU_TITLE, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void succeedRuValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.RU);

        titleCaseConstraintValidator.initialize(titleCase);

        assertTrue(titleCaseConstraintValidator.isValid(CORRECT_RU_TITLE, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void failEnValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.EN);

        titleCaseConstraintValidator.initialize(titleCase);

        assertFalse(titleCaseConstraintValidator.isValid(NOT_CORRECT_EN_TITLE, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void successEnValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.EN);

        titleCaseConstraintValidator.initialize(titleCase);

        assertTrue(titleCaseConstraintValidator.isValid(CORRECT_EN_TITLE, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void falseAnyValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.ANY);

        titleCaseConstraintValidator.initialize(titleCase);

        assertFalse(titleCaseConstraintValidator.isValid(NOT_CORRECT_EN_TITLE, mock(ConstraintValidatorContext.class)));
        assertFalse(titleCaseConstraintValidator.isValid(NOT_CORRECT_RU_TITLE, mock(ConstraintValidatorContext.class)));
    }

    @Test
    void successAnyValidation() {
        when(titleCase.titleCaseValue())
                .thenReturn(TitleCaseValue.ANY);

        titleCaseConstraintValidator.initialize(titleCase);

        assertTrue(titleCaseConstraintValidator.isValid(CORRECT_EN_TITLE, mock(ConstraintValidatorContext.class)));
        assertTrue(titleCaseConstraintValidator.isValid(CORRECT_RU_TITLE, mock(ConstraintValidatorContext.class)));
    }

}