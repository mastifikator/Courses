package com.mts.teta.courses.dto;

import javax.validation.constraints.NotBlank;

public class UserRequestToUpdate {
    @NotBlank(message = "username must be filled")
    private String username;

    @NotBlank(message = "password must be filled")
    private String password;

    public UserRequestToUpdate() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequestToCreate{" +
                "username='" + username + '\'' +
                '}';
    }
}
