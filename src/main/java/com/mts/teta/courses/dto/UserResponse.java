package com.mts.teta.courses.dto;

public class UserResponse {
    private Long Id;
    private String username;
    private String actionDescription;

    public UserResponse(Long id, String username, String actionDescription) {
        Id = id;
        this.username = username;
        this.actionDescription = actionDescription;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
