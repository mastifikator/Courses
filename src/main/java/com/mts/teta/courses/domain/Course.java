package com.mts.teta.courses.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long course_id;

    @Column
    private String author;

    @Column
    private String title;

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @ManyToMany
    @JoinTable(
            name = "courses_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users;

    public Course() {
    }

    public Course(Long course_id, String author, String title) {
        this.course_id = course_id;
        this.author = author;
        this.title = title;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long id) {
        this.course_id = id;
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

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(course_id, course.course_id) && Objects.equals(author, course.author) && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id, author, title);
    }
}
