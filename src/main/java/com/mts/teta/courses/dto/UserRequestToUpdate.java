package com.mts.teta.courses.dto;

import javax.validation.constraints.NotBlank;

public class UserRequestToUpdate {
    @NotBlank(message = "username must be filled")
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserRequestToCreate{" +
                "username='" + username + '\'' +
                '}';
    }
}
