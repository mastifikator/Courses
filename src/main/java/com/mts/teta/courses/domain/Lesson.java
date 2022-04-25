package com.mts.teta.courses.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String author;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column
    private String changeAuthor;

    @Column(name = "date_changed")
    private Timestamp dateChanged;

    @ManyToOne(optional = false)
    @JoinColumn(name = "module_id")
    private Module module;

    @ManyToMany
    @JoinTable(
            name = "lessons_users",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserPrincipal> users = new HashSet<>();

    public Lesson() {
    }

    public Lesson(String title, String text, String author, Module module) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
        this.module = module;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long id) {
        this.lessonId = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Set<UserPrincipal> getUsers() {
        return users;
    }

    public void setUsers(Set<UserPrincipal> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(lessonId, lesson.lessonId) && Objects.equals(title, lesson.title) && Objects.equals(text, lesson.text) && Objects.equals(author, lesson.author) && Objects.equals(dateCreated, lesson.dateCreated) && Objects.equals(module, lesson.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId, title, text, author, dateCreated, module);
    }
}
