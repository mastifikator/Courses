package com.mts.teta.courses.dto;

public class CourseResponse {
    private Long id;
    private String author;
    private String title;
    private String actionDescription;

    public CourseResponse(Long id, String author, String title, String actionDescription) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.actionDescription = actionDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
