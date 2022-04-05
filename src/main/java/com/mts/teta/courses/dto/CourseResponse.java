package com.mts.teta.courses.dto;

import java.util.Set;

public class CourseResponse {
    private Long id;
    private String author;
    private String title;
    private String actionDescription;
    private Set<UserResponse> assigned_users;

    public CourseResponse(Long id, String author, String title, String actionDescription, Set<UserResponse> assigned_users) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.actionDescription = actionDescription;
        this.assigned_users = assigned_users;
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

    public Set<UserResponse> getAssigned_users() {
        return assigned_users;
    }

    public void setAssigned_users(Set<UserResponse> assigned_users) {
        this.assigned_users = assigned_users;
    }
}
