package com.mts.teta.courses.handler;

import java.util.ArrayList;
import java.util.List;

public class HandleTitleCaseValidate {

    public static boolean commonValidate(String title) {
        if (!checkOnForbiddenCharacters(title)) {
            return false;
        }

        if (!checkOnNumberOfSpaces(title)) {
            return false;
        }

        if (title.startsWith(" ") || title.endsWith(" ")) {
            return false;
        }

        return checkOnEnglishAlphabet(title) || checkOnRussianAlphabet(title);
    }

    public static boolean enValidate(String title) {
        return checkOnUpperFirst(title) && checkOnUpperLast(title) && checkOnUpperAllExcludePretext(title);
    }

    public static boolean ruValidate(String title) {
        return checkOnUpperFirst(title) && checkOnLowerNotTheFirst(title);
    }

    public static boolean checkOnForbiddenCharacters(String title) {
        return !title.contains("\\r") && !title.contains("\\t") && !title.contains("\\n");
    }

    public static boolean checkOnNumberOfSpaces(String title) {
        return !title.matches(".*\\s{2,}.*");
    }

    public static boolean checkOnEnglishAlphabet(String title) {
        return title.matches("^[a-zA-Z0-9\"',:!?. ]+$");
    }

    public static boolean checkOnRussianAlphabet(String title) {
        return title.matches("^[а-яА-Я0-9\"',:!?. ]+$");
    }

    public static boolean checkOnUpperFirst(String title) {
        return Character.isUpperCase(title.charAt(0));
    }

    public static boolean checkOnLowerNotTheFirst(String title) {
        String[] titleSplitArray = title.split(" ");

        for (int i = 1; i < titleSplitArray.length; i++) {
            if (Character.isUpperCase(titleSplitArray[i].charAt(0))) {
                return false;
            }
        }

        return true;
    }

    public static boolean checkOnUpperLast(String title) {
        String[] titleSplitArray = title.split(" ");
        return Character.isUpperCase(titleSplitArray[titleSplitArray.length - 1].charAt(0));
    }

    public static boolean checkOnUpperAllExcludePretext(String title) {
        List<String> excludePretext = new ArrayList<>();
        excludePretext.add("a");
        excludePretext.add("but");
        excludePretext.add("for");
        excludePretext.add("or");
        excludePretext.add("not");
        excludePretext.add("the");
        excludePretext.add("an");

        String[] titleSplitArray = title.split(" ");

        for (String word : titleSplitArray) {
            if (!excludePretext.contains(word) && !Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }

        return true;
    }

}
