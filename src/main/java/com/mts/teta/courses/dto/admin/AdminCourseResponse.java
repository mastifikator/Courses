package com.mts.teta.courses.dto.admin;

import com.mts.teta.courses.dto.UserResponse;

import java.sql.Timestamp;
import java.util.Set;

public class AdminCourseResponse {
    private Long id;
    private String author;
    private String title;
    private String description;
    private Timestamp dateCreated;
    private String changeAuthor;
    private Timestamp dateChanged;
    private String tag;
    private String actionDescription;
    private Set<UserResponse> assignedUsers;

    public AdminCourseResponse(Long id,
                               String author,
                               String title,
                               String description,
                               Timestamp dateCreated,
                               String changeAuthor,
                               Timestamp dateChanged,
                               String tag,
                               String actionDescription,
                               Set<UserResponse> assignedUsers) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.changeAuthor = changeAuthor;
        this.dateChanged = dateChanged;
        this.tag = tag;
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

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getChangeAuthor() {
        return changeAuthor;
    }

    public void setChangeAuthor(String changeAuthor) {
        this.changeAuthor = changeAuthor;
    }

    public Timestamp getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Timestamp dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Set<UserResponse> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<UserResponse> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }
}
