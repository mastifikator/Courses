package com.mts.teta.courses.dto;

import com.mts.teta.courses.handler.annotation.TitleCase;
import com.mts.teta.courses.handler.annotation.TitleCaseValue;

import javax.validation.constraints.NotBlank;

public class LessonRequestToUpdate {
    @TitleCase(titleCaseValue = TitleCaseValue.ANY)
    private String title;
    @NotBlank(message = "Lesson text has to be filled")
    private String text;
    @NotBlank(message = "Lesson author has to be filled")
    private String author;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "LessonRequestToUpdate{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
