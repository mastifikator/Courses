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

    @NotNull(message = "CourseId has to be filled")
    private Long courseId;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "LessonRequestToCreate{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", courseId=" + courseId +
                '}';
    }
}
