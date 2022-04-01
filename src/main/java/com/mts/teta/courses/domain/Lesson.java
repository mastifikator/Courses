package com.mts.teta.courses.domain;

import javax.persistence.*;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lesson_id;

    @Column
    private String title;

    @Lob
    @Column
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    public Lesson() {
    }

    public Lesson(String title, String text, Course course) {
        this.title = title;
        this.text = text;
        this.course = course;
    }

    public Long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Long id) {
        this.lesson_id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
