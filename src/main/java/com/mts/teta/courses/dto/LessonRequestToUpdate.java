package com.mts.teta.courses.dto;

import com.mts.teta.courses.handler.annotation.TitleCase;
import com.mts.teta.courses.handler.annotation.TitleCaseValue;

import javax.validation.constraints.NotBlank;

public class LessonRequestToUpdate {
    @TitleCase(titleCaseValue = TitleCaseValue.ANY)
    private String title;
    @NotBlank(message = "Lesson text has to be filled")
    private String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "LessonRequestToUpdate{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
