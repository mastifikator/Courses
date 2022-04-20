package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.*;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class PreparedDatabase {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected LessonRepository lessonRepository;

    @Autowired
    protected ModuleRepository moduleRepository;

    @Autowired
    protected CourseRepository courseRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @BeforeEach
    @Transactional
    void prepareDatabase() {
        Course course1 = new Course("Author1", "Title1", "Description1");
        Course course2 = new Course("Author2", "Title2", "Description2");
        Course course3 = new Course("Author3", "Title3", "Description3");
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courseRepository.saveAllAndFlush(courses);

        Module module1 = new Module("Title1", "Author1", "Description1", course1);
        Module module2 = new Module("Title2", "Author2", "Description2", course2);
        Module module3 = new Module("Title3", "Author3", "Description3", course3);
        ArrayList<Module> modules = new ArrayList<>();
        modules.add(module1);
        modules.add(module2);
        modules.add(module3);
        moduleRepository.saveAllAndFlush(modules);

        Lesson lesson1 = new Lesson("Title1", "Text1", "Author1", module1);
        Lesson lesson2 = new Lesson("Title2", "Text2", "Author2", module2);
        Lesson lesson3 = new Lesson("Title3", "Text3", "Author3", module3);
        ArrayList<Lesson> lessons = new ArrayList<>();
        lessons.add(lesson1);
        lessons.add(lesson2);
        lessons.add(lesson3);
        lessonRepository.saveAllAndFlush(lessons);

        Role role1 = new Role("Role1");
        Role role2 = new Role("Role2");
        Role role3 = new Role("Role3");
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(role1);
        roles.add(role2);
        roles.add(role3);
        roleRepository.saveAllAndFlush(roles);

        UserPrincipal userPrincipal1 = new UserPrincipal("user1", "pass1", "nickname1", "email1");
        UserPrincipal userPrincipal2 = new UserPrincipal("user2", "pass2", "nickname2", "email2");
        UserPrincipal userPrincipal3 = new UserPrincipal("user3", "pass3", "nickname3", "email3");
        ArrayList<UserPrincipal> users = new ArrayList<>();
        users.add(userPrincipal1);
        users.add(userPrincipal2);
        users.add(userPrincipal3);
        userRepository.saveAllAndFlush(users);

    }

    @AfterEach
    @Transactional
    void clearDatabase() {
        jdbcTemplate.execute("TRUNCATE TABLE lessons CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE modules CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE courses CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE users CASCADE");
        jdbcTemplate.execute("TRUNCATE TABLE roles CASCADE");

        jdbcTemplate.execute("ALTER SEQUENCE lessons_lesson_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE modules_module_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE courses_course_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE users_user_id_seq RESTART");
        jdbcTemplate.execute("ALTER SEQUENCE roles_role_id_seq RESTART");
    }
}
