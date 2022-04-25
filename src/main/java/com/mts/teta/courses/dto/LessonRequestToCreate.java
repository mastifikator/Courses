package com.mts.teta.courses.dto;

import com.mts.teta.courses.handler.annotation.TitleCase;
import com.mts.teta.courses.handler.annotation.TitleCaseValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LessonRequestToCreate {

    @TitleCase(titleCaseValue = TitleCaseValue.ANY)
    private String title;

    @NotBlank(message = "Text lesson has to be filled")
    private String text;

    @NotBlank(message = "Author lesson has to be filled")
    private String author;

    @NotNull(message = "ModuleId has to be filled")
    private Long moduleId;

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

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

}
