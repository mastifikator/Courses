package com.mts.teta.courses.service;

import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.ModuleRequestToCreate;
import com.mts.teta.courses.dto.ModuleRequestToUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModuleListerTest extends PreparedDatabase {

    @Autowired
    ModuleLister moduleLister;

    @Test
    @Transactional
    void createModule() {
        ModuleRequestToCreate moduleRequestToCreate = new ModuleRequestToCreate();
        moduleRequestToCreate.setTitle("Title4");
        moduleRequestToCreate.setAuthor("Author4");
        moduleRequestToCreate.setDescription("Description4");
        moduleRequestToCreate.setCourseId(3L);

        Module module = moduleLister.createModule(moduleRequestToCreate);

        assertTrue(moduleRepository.findAll().contains(module));
    }

    @Test
    @Transactional
    void updateModule() {
        ModuleRequestToUpdate moduleRequestToUpdate = new ModuleRequestToUpdate();
        moduleRequestToUpdate.setTitle("Title3_new");
        moduleRequestToUpdate.setAuthor("Author3_new");
        moduleRequestToUpdate.setDescription("Description3_new");

        Module module = moduleLister.updateModule(3L, moduleRequestToUpdate);

        assertEquals("Title3_new", moduleLister.moduleById(3L).getTitle());
    }

    @Test
    @Transactional
    void moduleById() {
        Module module = moduleLister.moduleById(1L);

        assertEquals("Title1", module.getTitle());
    }

    @Test
    @Transactional
    void deleteModule() {
        ModuleRequestToCreate moduleRequestToCreate = new ModuleRequestToCreate();
        moduleRequestToCreate.setTitle("Title5");
        moduleRequestToCreate.setAuthor("Author5");
        moduleRequestToCreate.setDescription("Description5");
        moduleRequestToCreate.setCourseId(3L);

        int countModuleBeforeCreate = moduleRepository.findAll().size();
        Module module = moduleLister.createModule(moduleRequestToCreate);
        moduleLister.deleteModule(module.getModuleId());

        assertEquals(countModuleBeforeCreate, moduleRepository.findAll().size());
    }

    @Test
    @Transactional
    void modulesByCourseId() {
        List<Module> modules = moduleLister.modulesByCourseId(1L);

        assertEquals(1, modules.size());
    }
}