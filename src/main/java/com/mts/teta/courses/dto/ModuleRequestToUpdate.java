package com.mts.teta.courses.dto;

import com.mts.teta.courses.handler.annotation.TitleCase;
import com.mts.teta.courses.handler.annotation.TitleCaseValue;

import javax.validation.constraints.NotBlank;

public class ModuleRequestToUpdate {
    @TitleCase(titleCaseValue = TitleCaseValue.ANY)
    private String title;

    @NotBlank(message = "Author has to be filled")
    private String author;

    @NotBlank(message = "Description has to be filled")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
