package com.mts.teta.courses.dto;

import com.mts.teta.courses.handler.annotation.TitleCase;
import com.mts.teta.courses.handler.annotation.TitleCaseValue;

import javax.validation.constraints.NotBlank;

public class CourseRequestToUpdate {
    @NotBlank(message = "Course author has to be filled")
    private String author;
    @TitleCase(titleCaseValue = TitleCaseValue.ANY)
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "CourseRequestToUpdate{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
