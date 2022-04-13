package com.mts.teta.courses.dto;

public class LessonResponse {
    private Long id;
    private String title;
    private String text;
    private Long moduleId;
    private String actionDescription;

    public LessonResponse(Long id, String title, String text, Long moduleId, String actionDescription) {
        this.id = id;
        this.title = title;
        this.text = text;
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
