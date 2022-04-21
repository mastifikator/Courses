package com.mts.teta.courses.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column
    private String author;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Integer rating;

    @Column
    private String tag;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @Column(name = "change_author")
    private String changeAuthor;

    @Column(name = "date_changed")
    private Timestamp dateChanged;

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Module> modules;

    @ManyToMany
    @JoinTable(
            name = "courses_users",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserPrincipal> users = new HashSet<>();

    public Course() {
    }

    public Course(String author, String title, String description) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long id) {
        this.courseId = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Set<UserPrincipal> getUsers() {
        return users;
    }

    public void setUsers(Set<UserPrincipal> userPrincipals) {
        this.users = userPrincipals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) && Objects.equals(author, course.author) && Objects.equals(title, course.title) && Objects.equals(description, course.description) && Objects.equals(rating, course.rating) && Objects.equals(tag, course.tag) && Objects.equals(dateCreated, course.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, author, title, description, rating, tag, dateCreated);
    }
}
