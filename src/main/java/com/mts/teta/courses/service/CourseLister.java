package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.CourseRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.domain.Role;
import com.mts.teta.courses.domain.UserPrincipal;
import com.mts.teta.courses.dto.CourseRequestToCreate;
import com.mts.teta.courses.dto.CourseRequestToUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CourseLister {

    private final CourseRepository repository;
    private final UserLister userLister;

    @Autowired
    public CourseLister(CourseRepository repository, UserLister userLister) {
        this.repository = repository;
        this.userLister = userLister;
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Set<Course> getAllCoursesForUser(UserPrincipal user) {

        if (userLister.isStudent(user)) {
            return user.getCourses();
        } else {
            return new HashSet<>(repository.findAll());
        }
    }

    public List<Course> coursesByTitlePrefixForUser(UserPrincipal user, String titlePrefix) {

        if (userLister.isStudent(user)) {
            return user.getCourses().stream()
                    .filter(c -> c.getTitle().contains(titlePrefix))
                    .collect(Collectors.toList());
        } else {
            return repository.findByTitleLike("%" + titlePrefix + "%");
        }
    }

    public Course courseById(Long courseId) {
        return repository.findById(courseId).orElseThrow();
    }

    public List<Course> coursesByTitlePrefix(String titlePrefix) {
        return repository.findByTitleLike("%" + titlePrefix + "%");
    }

    public Course updateCourse(Long courseId, CourseRequestToUpdate request) {
        Course course = repository.getById(courseId);

        if (!request.getAuthor().equals("")) {
            course.setAuthor(request.getAuthor());
        }
        if (!request.getTitle().equals("")) {
            course.setTitle(request.getTitle());
        }
        if (!request.getDescription().equals("")) {
            course.setDescription(request.getDescription());
        }
        if (!request.getTag().equals("")) {
            course.setTag(request.getTag());
        }
        course.setDateChanged(new Timestamp(System.currentTimeMillis()));

        repository.save(course);
        return course;
    }

    public Course createCourse(CourseRequestToCreate request) {
        Course course = new Course(request.getAuthor(),
                request.getTitle(),
                request.getDescription(),
                request.getTag());

        repository.save(course);
        return course;
    }

    public Course assignedUserToCourse(Long courseId, Long userId) {
        UserPrincipal userPrincipal = userLister.userById(userId);
        Course course = courseById(courseId);

        course.getUsers().add(userPrincipal);
        userPrincipal.getCourses().add(course);
        userLister.saveUser(userPrincipal);

        return saveCourse(course);
    }

    public Course unassignedUserToCourse(Long courseId, Long userId) {
        UserPrincipal userPrincipal = userLister.userById(userId);
        Course course = courseById(courseId);

        course.getUsers().remove(userPrincipal);
        return saveCourse(course);
    }

    public Set<UserPrincipal> getUsersFromCourse(Long courseId) {
        return courseById(courseId).getUsers();
    }

    public List<Module> getModulesFromCourse(Long courseId) {
        return courseById(courseId).getModules();
    }

    public void deleteCourse(Long courseId) {
        repository.deleteById(courseId);
    }

    public Course saveCourse(Course course) {
        return repository.saveAndFlush(course);
    }
}
