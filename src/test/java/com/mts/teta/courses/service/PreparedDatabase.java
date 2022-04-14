package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.*;
import com.mts.teta.courses.domain.*;
import com.mts.teta.courses.domain.Module;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class PreparedDatabase {

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

    @BeforeAll
    void prepareDatabase() {
        Course course1 = new Course("Author1", "Title1");
        Course course2 = new Course("Author2", "Title2");
        Course course3 = new Course("Author3", "Title3");

        Module module1 = new Module("Title1", "Author1", "Description1", course1);
        Module module2 = new Module("Title2", "Author2", "Description2", course2);
        Module module3 = new Module("Title3", "Author3", "Description3", course3);

        Lesson lesson1 = new Lesson("Title1", "Text1", module1);
        Lesson lesson2 = new Lesson("Title2", "Text2", module2);
        Lesson lesson3 = new Lesson("Title3", "Text3", module3);

        Role role1 = new Role("Role1");
        Role role2 = new Role("Role2");
        Role role3 = new Role("Role3");

        UserPrincipal userPrincipal1 = new UserPrincipal("user1", "pass1");
        UserPrincipal userPrincipal2 = new UserPrincipal("user2", "pass2");
        UserPrincipal userPrincipal3 = new UserPrincipal("user3", "pass3");

        module1.setLessons(List.of(lesson1));
        module2.setLessons(List.of(lesson2));
        module3.setLessons(List.of(lesson3));

        course1.setModules(List.of(module1));
        course2.setModules(List.of(module2));
        course3.setModules(List.of(module3));

        course1.setUsers(Set.of(userPrincipal1));
        course2.setUsers(Set.of(userPrincipal2));
        course3.setUsers(Set.of(userPrincipal3));

        userRepository.saveAll(List.of(userPrincipal1, userPrincipal2, userPrincipal3));
        courseRepository.saveAll(List.of(course1, course2, course3));
        moduleRepository.saveAll(List.of(module1, module2, module3));
        lessonRepository.saveAll(List.of(lesson1, lesson2, lesson3));
        roleRepository.saveAll(List.of(role1, role2, role3));
    }
}
