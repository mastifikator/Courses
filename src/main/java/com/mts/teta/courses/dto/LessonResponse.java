package com.mts.teta.courses.dto;

public class LessonResponse {
    private Long id;
    private String title;
    private String text;
    private Long course_id;
    private String actionDescription;

    public LessonResponse(Long id, String title, String text, Long course_id, String actionDescription) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.course_id = course_id;
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

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
