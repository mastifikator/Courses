package com.mts.teta.courses.handler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HandleTitleCaseValidateTest {

    @Test
    void falseCommonValidate() {
        assertFalse(HandleTitleCaseValidate.commonValidate("Forbidden Character \\n"));
        assertFalse(HandleTitleCaseValidate.commonValidate("Too Many     Whitespaces"));
        assertFalse(HandleTitleCaseValidate.commonValidate(" Whitespaces On begin"));
        assertFalse(HandleTitleCaseValidate.commonValidate("Whitespaces On Last "));
        assertFalse(HandleTitleCaseValidate.commonValidate("Русские words"));
        assertFalse(HandleTitleCaseValidate.commonValidate("EnglishСлова"));
    }

    @Test
    void successCommonValidate() {
        assertTrue(HandleTitleCaseValidate.commonValidate("Это правильный русский заголовок"));
        assertTrue(HandleTitleCaseValidate.commonValidate("It a Right English Title"));
        assertTrue(HandleTitleCaseValidate.commonValidate("It Right English Title"));
    }

    @Test
    void falseEnValidate() {
        assertFalse(HandleTitleCaseValidate.enValidate("first word small"));
        assertFalse(HandleTitleCaseValidate.enValidate("Last word small"));
        assertFalse(HandleTitleCaseValidate.enValidate("first and last word small"));
        assertFalse(HandleTitleCaseValidate.enValidate("Middle A word Small"));
    }

    @Test
    void successEnValidate() {
        assertTrue(HandleTitleCaseValidate.enValidate("All Word not Small"));
        assertTrue(HandleTitleCaseValidate.enValidate("All Word a Big"));
        assertTrue(HandleTitleCaseValidate.enValidate("All Word the Big"));
    }

    @Test
    void falseRuValidate() {
        assertFalse(HandleTitleCaseValidate.ruValidate("первое слово с маленькой"));
        assertFalse(HandleTitleCaseValidate.ruValidate("Первое Слово с Большой И Остальные С Большой"));
        assertFalse(HandleTitleCaseValidate.ruValidate("пеРвое слОво с малеНькой, в сЕрЕдине"));
    }

    @Test
    void successRuValidate() {
        assertTrue(HandleTitleCaseValidate.ruValidate("Первое слово с большой, остальные с маленькой"));
    }

    @Test
    void falseCheckOnForbiddenCharacters() {
        assertFalse(HandleTitleCaseValidate.checkOnForbiddenCharacters("Это \\r"));
        assertFalse(HandleTitleCaseValidate.checkOnForbiddenCharacters("Это \\t"));
        assertFalse(HandleTitleCaseValidate.checkOnForbiddenCharacters("Это \\n"));
    }

    @Test
    void trueCheckOnForbiddenCharacters() {
        assertTrue(HandleTitleCaseValidate.checkOnForbiddenCharacters("Здесь нет r"));
        assertTrue(HandleTitleCaseValidate.checkOnForbiddenCharacters("Здесь нет t"));
        assertTrue(HandleTitleCaseValidate.checkOnForbiddenCharacters("Здесь нет n"));
    }

    @Test
    void falseCheckOnNumberOfSpaces() {
        assertFalse(HandleTitleCaseValidate.checkOnNumberOfSpaces("Это  чудесный день"));
        assertFalse(HandleTitleCaseValidate.checkOnNumberOfSpaces("Это  чудесный   день"));
        assertFalse(HandleTitleCaseValidate.checkOnNumberOfSpaces("Это чудесный   день"));
        assertFalse(HandleTitleCaseValidate.checkOnNumberOfSpaces("Hello     validation!"));
    }

    @Test
    void successCheckOnNumberOfSpaces() {
        assertTrue(HandleTitleCaseValidate.checkOnNumberOfSpaces("Это чудесный день!"));
        assertTrue(HandleTitleCaseValidate.checkOnNumberOfSpaces("Привет валидация!"));
        assertTrue(HandleTitleCaseValidate.checkOnNumberOfSpaces("Привет!"));
    }

    @Test
    void falseCheckOnEnglishAlphabet() {
        assertFalse(HandleTitleCaseValidate.checkOnEnglishAlphabet("Привет валидация!"));
        assertFalse(HandleTitleCaseValidate.checkOnEnglishAlphabet("Hello валидация:"));
        assertFalse(HandleTitleCaseValidate.checkOnEnglishAlphabet("Helloпривет validation"));
        assertFalse(HandleTitleCaseValidate.checkOnEnglishAlphabet("This is a wonderful день"));
    }

    @Test
    void successCheckOnEnglishAlphabet() {
        assertTrue(HandleTitleCaseValidate.checkOnEnglishAlphabet("Hello validation!"));
        assertTrue(HandleTitleCaseValidate.checkOnEnglishAlphabet("Hello validation, how are you?"));
        assertTrue(HandleTitleCaseValidate.checkOnEnglishAlphabet("Hello validation, 'how are you': "));
        assertTrue(HandleTitleCaseValidate.checkOnEnglishAlphabet("This is a wonderful day... \""));
    }

    @Test
    void falseCheckOnRussianAlphabet() {
        assertFalse(HandleTitleCaseValidate.checkOnRussianAlphabet("Hello validation!"));
        assertFalse(HandleTitleCaseValidate.checkOnRussianAlphabet("Hello валидация:"));
        assertFalse(HandleTitleCaseValidate.checkOnRussianAlphabet("Helloпривет validation"));
        assertFalse(HandleTitleCaseValidate.checkOnRussianAlphabet("This is a wonderful день"));
    }

    @Test
    void successCheckOnRussianAlphabet() {
        assertTrue(HandleTitleCaseValidate.checkOnRussianAlphabet("Привет валидация!"));
        assertTrue(HandleTitleCaseValidate.checkOnRussianAlphabet("Привет валидация, как дела?"));
        assertTrue(HandleTitleCaseValidate.checkOnRussianAlphabet("Привет валидация, 'как дела'"));
        assertTrue(HandleTitleCaseValidate.checkOnRussianAlphabet("Привет: как дела  \""));
    }

    @Test
    void falseCheckOnUpperFirst() {
        assertFalse(HandleTitleCaseValidate.checkOnUpperFirst("hello validation"));
        assertFalse(HandleTitleCaseValidate.checkOnUpperFirst("hello Validation"));
    }

    @Test
    void successCheckOnUpperFirst() {
        assertTrue(HandleTitleCaseValidate.checkOnUpperFirst("Hello validation"));
        assertTrue(HandleTitleCaseValidate.checkOnUpperFirst("Hello Validation"));
    }

    @Test
    void falseCheckOnLowerNotTheFirst() {
        assertFalse(HandleTitleCaseValidate.checkOnLowerNotTheFirst("Hello Validation"));
        assertFalse(HandleTitleCaseValidate.checkOnLowerNotTheFirst("hello Validation"));
        assertFalse(HandleTitleCaseValidate.checkOnLowerNotTheFirst("hello Not Validation"));
    }

    @Test
    void successCheckOnLowerNotTheFirst() {
        assertTrue(HandleTitleCaseValidate.checkOnLowerNotTheFirst("Hello validation"));
        assertTrue(HandleTitleCaseValidate.checkOnLowerNotTheFirst("hello validation"));
        assertTrue(HandleTitleCaseValidate.checkOnLowerNotTheFirst("hello not validation"));
    }

    @Test
    void falseCheckOnUpperLast() {
        assertFalse(HandleTitleCaseValidate.checkOnUpperLast("hello validation"));
        assertFalse(HandleTitleCaseValidate.checkOnUpperLast("Hello Validation on"));
    }


    @Test
    void successCheckOnUpperLast() {
        assertTrue(HandleTitleCaseValidate.checkOnUpperLast("hello Validation"));
        assertTrue(HandleTitleCaseValidate.checkOnUpperLast("hello Validation On"));
    }

    @Test
    void falseCheckOnUpperAllExcludePretext() {
        assertFalse(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("Hello a validation"));
        assertFalse(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("hello or Validation"));
        assertFalse(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("Hello note Validation"));
    }

    @Test
    void successCheckOnUpperAllExcludePretext() {
        assertTrue(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("Hello Or Validation"));
        assertTrue(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("Hello or Validation"));
        assertTrue(HandleTitleCaseValidate.checkOnUpperAllExcludePretext("Hello the Validation"));
    }
}