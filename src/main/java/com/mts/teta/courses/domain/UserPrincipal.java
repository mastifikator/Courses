package com.mts.teta.courses.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserPrincipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Course> courses = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles = new HashSet<>();

    public UserPrincipal() {
    }

    public UserPrincipal(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal userPrincipal = (UserPrincipal) o;
        return Objects.equals(userId, userPrincipal.userId) && Objects.equals(username, userPrincipal.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }
}