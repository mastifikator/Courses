package com.mts.teta.courses.dto;

public class UserResponse {
    private Long Id;
    private String username;
    private String nickname;
    private String email;
    private String actionDescription;

    public UserResponse(Long id, String username, String nickname, String email, String actionDescription) {
        Id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }
}
