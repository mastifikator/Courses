package com.mts.teta.courses.dto;

import java.util.Set;

public class CourseResponse {
    private Long id;
    private String author;
    private String title;
    private String description;
    private String actionDescription;
    private Set<UserResponse> assignedUsers;

    public CourseResponse(Long id,
                          String author,
                          String title,
                          String description,
                          String actionDescription,
                          Set<UserResponse> assignedUsers) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.actionDescription = actionDescription;
        this.assignedUsers = assignedUsers;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Set<UserResponse> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<UserResponse> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
