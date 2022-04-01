package com.mts.teta.courses.dao;

import com.mts.teta.courses.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select c.lessons " +
            "from Course c " +
            "where c.course_id = :id")
    List<Lesson> findAllLessonsForCourse(Long id);
}
