package com.mts.teta.courses.dto;

public class RoleResponse {
    private Long Id;
    private String roleName;
    private String actionDescription;

    public RoleResponse(Long id, String roleName, String actionDescription) {
        Id = id;
        this.roleName = roleName;
        this.actionDescription = actionDescription;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return roleName;
    }

    public void setUsername(String username) {
        this.roleName = username;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
