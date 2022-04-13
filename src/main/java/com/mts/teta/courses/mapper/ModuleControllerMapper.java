package com.mts.teta.courses.mapper;

import com.mts.teta.courses.domain.Module;
import com.mts.teta.courses.dto.ModuleResponse;
import org.springframework.stereotype.Component;

@Component
public class ModuleControllerMapper {

    public ModuleResponse mapModuleToModuleResponse(Module module, String actionResponse) {
        return new ModuleResponse(module.getModuleId(),
                module.getTitle(),
                module.getAuthor(),
                module.getDescription(),
                module.getCourse().getCourseId(),
                actionResponse);
    }
}
