package com.mts.teta.courses.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id")
    private Long moduleId;

    @Column
    private String title;

    @Column
    private String author;

    @Column
    private String description;

    @Column(name = "date_created")
    private Timestamp dateCreated;

    @OneToMany(mappedBy = "module", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Lesson> lessons;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    public Module() {
    }

    public Module(String title, String author, String description, Course course) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.course = course;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(moduleId, module.moduleId) && Objects.equals(title, module.title) && Objects.equals(author, module.author) && Objects.equals(description, module.description) && Objects.equals(dateCreated, module.dateCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(moduleId, title, author, description, dateCreated);
    }
}
