package com.mts.teta.courses.service;

import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseListerTest extends PreparedDatabase {

    @Autowired
    protected CourseLister courseLister;

    @Test
    @Transactional
    void courseById() {
        Course course = courseLister.courseById(1L);

        assertEquals(course.getTitle(), "Title1");
        assertEquals(course.getAuthor(), "Author1");
    }

    @Test
    @Transactional
    void coursesByTitlePrefix() {
        List<Course> courses = courseLister.coursesByTitlePrefix("Title1");

        assertEquals("Author1", courses.get(0).getAuthor());
    }

    @Test
    @Transactional
    void updateCourse() {
        CourseRequestToUpdate courseRequestToUpdate = new CourseRequestToUpdate();
        courseRequestToUpdate.setTitle("Title3_new");
        courseRequestToUpdate.setAuthor("Author3_new");

        Course course = courseLister.updateCourse(3L, courseRequestToUpdate);

        assertEquals(course.getAuthor(), courseLister.courseById(3L).getAuthor());
    }

    @Test
    @Transactional
    void createCourse() {
        CourseRequestToCreate courseRequestToCreate = new CourseRequestToCreate();
        courseRequestToCreate.setTitle("Title4");
        courseRequestToCreate.setAuthor("Author4");

        Course course = courseLister.createCourse(courseRequestToCreate);

        assertTrue(courseRepository.findAll().contains(course));
    }

    @Test
    @Transactional
    void assignedUserToCourse() {
        courseLister.assignedUserToCourse(1L, 1L);

        assertEquals(1, userRepository.getById(1L).getCourses().size());
    }

    @Test
    @Transactional
    void unassignedUserToCourse() {
        courseLister.assignedUserToCourse(1L, 1L);
        courseLister.unassignedUserToCourse(1L, 1L);

        assertEquals(1, userRepository.getById(1L).getCourses().size());
    }

    @Test
    @Transactional
    void getUsersFromCourse() {
        courseLister.assignedUserToCourse(1L, 1L);

        Set<UserPrincipal> users = courseLister.getUsersFromCourse(1L);

        assertEquals(users.size(), 1);
    }

    @Test
    @Transactional
    void deleteCourse() {
        CourseRequestToCreate courseRequestToCreate = new CourseRequestToCreate();
        courseRequestToCreate.setTitle("Title5");
        courseRequestToCreate.setAuthor("Author5");

        int countCourseBeforeCreate = courseRepository.findAll().size();
        Course course = courseLister.createCourse(courseRequestToCreate);
        courseLister.deleteCourse(course.getCourseId());

        assertEquals(countCourseBeforeCreate, courseRepository.findAll().size());
    }

    @Test
    @Transactional
    void saveCourse() {
        Course course = new Course("Author5", "Title5");
        courseLister.saveCourse(course);

        assertEquals(course, courseLister.coursesByTitlePrefix("Title5").get(0));
    }
}