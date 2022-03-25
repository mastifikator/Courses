package com.mts.teta.courses.handler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HandleTitleCaseValidateTest {

    @ParameterizedTest
    @ValueSource(strings = {"Forbidden Character \\n",
            "Too Many     Whitespaces",
            " Whitespaces On begin",
            "Whitespaces On Last ",
            "Русские words",
            "EnglishСлова"})
    void falseCommonValidate(String input) {
        assertFalse(HandleTitleCaseValidate.commonValidate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Это правильный русский заголовок",
            "It a Right English Title",
            "It Right English Title"})
    void successCommonValidate(String input) {
        assertTrue(HandleTitleCaseValidate.commonValidate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first word small",
            "Last word small",
            "first and last word small",
            "Middle A word Small"})
    void falseEnValidate(String input) {
        assertFalse(HandleTitleCaseValidate.enValidate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"All Word not Small",
            "All Word a Big",
            "All Word the Big"})
    void successEnValidate(String input) {
        assertTrue(HandleTitleCaseValidate.enValidate(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"первое слово с маленькой",
            "Первое Слово с Большой И Остальные С Большой",
            "пеРвое слОво с малеНькой, в сЕрЕдине"})
    void falseRuValidate(String input) {
        assertFalse(HandleTitleCaseValidate.ruValidate(input));
    }

    @Test
    void successRuValidate() {
        assertTrue(HandleTitleCaseValidate.ruValidate("Первое слово с большой, остальные с маленькой"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Это \\r",
            "Это \\t",
            "Это \\n"})
    void falseCheckOnForbiddenCharacters(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnForbiddenCharacters(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Здесь нет r",
            "Здесь нет t",
            "Здесь нет n"})
    void trueCheckOnForbiddenCharacters(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnForbiddenCharacters(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Это  чудесный день",
            "Это  чудесный   день",
            "Это чудесный   день",
            "Hello     validation!"})
    void falseCheckOnNumberOfSpaces(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnNumberOfSpaces(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Это чудесный день!",
            "Привет валидация!",
            "Привет!"})
    void successCheckOnNumberOfSpaces(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnNumberOfSpaces(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Привет валидация!",
            "Hello валидация:",
            "Helloпривет validation",
            "This is a wonderful день"})
    void falseCheckOnEnglishAlphabet(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnEnglishAlphabet(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello validation!",
            "Hello validation, how are you?",
            "Hello validation, 'how are you': ",
            "This is a wonderful day... \""})
    void successCheckOnEnglishAlphabet(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnEnglishAlphabet(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello validation!",
            "Hello валидация:",
            "Helloпривет validation",
            "This is a wonderful день"})
    void falseCheckOnRussianAlphabet(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnRussianAlphabet(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Привет валидация!",
            "Привет валидация, как дела?",
            "Привет валидация, 'как дела'",
            "Привет: как дела  \""})
    void successCheckOnRussianAlphabet(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnRussianAlphabet(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello validation",
            "hello Validation"})
    void falseCheckOnUpperFirst(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnUpperFirst(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello validation",
            "Hello Validation"})
    void successCheckOnUpperFirst(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnUpperFirst(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello Validation",
            "hello Validation",
            "hello Not Validation"})
    void falseCheckOnLowerNotTheFirst(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnLowerNotTheFirst(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello validation",
            "hello validation",
            "hello not validation"})
    void successCheckOnLowerNotTheFirst(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnLowerNotTheFirst(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello validation",
            "Hello Validation on"})
    void falseCheckOnUpperLast(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnUpperLast(input));
    }


    @ParameterizedTest
    @ValueSource(strings = {"hello Validation",
            "hello Validation On"})
    void successCheckOnUpperLast(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnUpperLast(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello a validation",
            "hello or Validation",
            "Hello note Validation"})
    void falseCheckOnUpperAllExcludePretext(String input) {
        assertFalse(HandleTitleCaseValidate.checkOnUpperAllExcludePretext(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello Or Validation",
            "Hello or Validation",
            "Hello the Validation"})
    void successCheckOnUpperAllExcludePretext(String input) {
        assertTrue(HandleTitleCaseValidate.checkOnUpperAllExcludePretext(input));
    }
}