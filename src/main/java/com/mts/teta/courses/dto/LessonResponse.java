package com.mts.teta.courses.dto;

public class LessonResponse {
    private Long id;
    private String title;
    private String text;
    private String author;
    private Long moduleId;
    private String actionDescription;

    public LessonResponse(Long id, String title, String text, String author, Long moduleId, String actionDescription) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.author = author;
        this.moduleId = moduleId;
        this.actionDescription = actionDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCourseId() {
        return moduleId;
    }

    public void setCourseId(Long courseId) {
        this.moduleId = courseId;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
