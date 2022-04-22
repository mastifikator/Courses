package com.mts.teta.courses.service;

import com.mts.teta.courses.dao.ModuleRepository;
import com.mts.teta.courses.domain.Course;
import com.mts.teta.courses.domain.Lesson;
import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import liquibase.pro.packaged.z;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleLister {

    @Autowired
    private CourseLister courseLister;

    @Autowired
    private ModuleRepository moduleRepository;

    public Module createModule(ModuleRequestToCreate request) {
        Course course = courseLister.courseById(request.getCourseId());
        Module module = new Module(request.getTitle(),
                request.getAuthor(),
                request.getDescription(),
                course);

        moduleRepository.save(module);
        return module;
    }

    public Module updateModule(Long moduleId, ModuleRequestToUpdate request) {
        Module module = moduleRepository.getById(moduleId);

        if (!request.getTitle().equals("")) {
            module.setTitle(request.getTitle());
        }
        if (!request.getAuthor().equals("")) {
            module.setAuthor(request.getAuthor());
        }
        if (!request.getDescription().equals("")) {
            module.setDescription(request.getDescription());
        }

        moduleRepository.save(module);
        return module;
    }

    public Module moduleById(Long moduleId) {
        return moduleRepository.getById(moduleId);
    }

    public void deleteModule(Long moduleId) {
        moduleRepository.deleteById(moduleId);
    }

    public List<Module> modulesByCourseId(Long courseId) {
        return moduleRepository.findAllModulesForCourse(courseId);
    }
}
