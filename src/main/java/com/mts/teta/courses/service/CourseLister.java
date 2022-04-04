package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.CourseRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.User;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class CourseLister {
    private final CourseRepository repository;

    @Autowired
    UserLister userLister;

    @Autowired
    public CourseLister(CourseRepository repository) {
        this.repository = repository;
    }

    public Course courseById(Long courseId) {
        return repository.findById(courseId).orElseThrow();
    }

    public List<Course> coursesByTitlePrefix(String titlePrefix) {
        return repository.findByTitleLike(titlePrefix + "%");
    }

    public Course updateCourse(Long courseId, CourseRequestToUpdate request) {
        Course course = repository.getById(courseId);
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

    public Course assignedUserToCourse(Long courseId, Long userId) {
        User user = userLister.userById(userId);
        Course course = courseById(courseId);

        course.getUsers().add(user);
        return saveCourse(course);
    }

    public Course unassignedUserToCourse(Long courseId, Long userId) {
        User user = userLister.userById(userId);
        Course course = courseById(courseId);

        course.getUsers().remove(user);
        return saveCourse(course);
    }

    public Set<User> getUsersFromCourse(Long courseId) {
        return courseById(courseId).getUsers();
    }

    public void deleteCourse(Long courseId) {
        repository.deleteById(courseId);
    }

    public Course saveCourse(Course course) {
        return repository.save(course);
    }
}
