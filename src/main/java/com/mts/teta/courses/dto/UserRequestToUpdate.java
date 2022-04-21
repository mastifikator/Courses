package com.mts.teta.courses.dto;

import com.mts.teta.courses.domain.Role;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class UserRequestToUpdate {
    @NotBlank(message = "username must be filled")
    private String username;

    @NotBlank(message = "password must be filled")
    private String password;

    @NotBlank(message = "nickname must be filled")
    private String nickname;

    @NotBlank(message = "email must be filled")
    private String email;

    @NotBlank(message = "roles must be filled")
    List<Role> roles;

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRequestToUpdate{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
