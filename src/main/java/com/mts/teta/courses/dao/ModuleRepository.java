package com.mts.teta.courses.dao;

import com.mts.teta.courses.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    @Query("select c.modules " +
            "from Course c " +
            "where c.courseId = :courseId")
    List<Module> findAllModulesForCourse(Long courseId);
}
