package com.mts.teta.courses.dto.UI;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RequestToRegistration {

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 5, max = 24)
    private String username;

    @NotBlank(message = "Никнейм не может быть пустым")
    @Size(min = 5, max = 24)
    private String nickname;

    @Email(message = "Введите верный почтовый адрес")
    private String email;

    @NotBlank(message = "Введите пароль")
    @Size(min = 5, max = 24)
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
