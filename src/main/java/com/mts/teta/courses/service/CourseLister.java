package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.CourseRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseLister {
    private final CourseRepository repository;

    @Autowired
    public CourseLister(CourseRepository repository) {
        this.repository = repository;
    }

    public Course courseById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public List<Course> coursesByTitlePrefix(String titlePrefix) {
        return repository.findByTitleWithPrefix(titlePrefix);
    }

    public Course updateCourse(Long id, CourseRequestToUpdate request) {
        Course course = repository.findById(id).orElseThrow();
        course.setAuthor(request.getAuthor());
        course.setTitle(request.getTitle());
        repository.save(course);
        return course;
    }

    public Course createCourse(CourseRequestToCreate request) {
        Course course = new Course(null, request.getAuthor(), request.getTitle());
        repository.save(course);
        return course;
    }

    public void deleteCourse(Long id) {
        repository.delete(id);
    }
}
