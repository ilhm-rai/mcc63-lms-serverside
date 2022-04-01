/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.model.dto.ModuleData;
import co.id.mii.mcc63lmsserverside.repository.ModuleRepository;
import co.id.mii.mcc63lmsserverside.model.Module;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Agung
 */
@Service
public class ModuleService {

    private ModuleRepository moduleRepository;
    private CourseService courseService;
    private ModelMapper modelMapper;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository, CourseService courseService, ModelMapper modelMapper) {
        this.moduleRepository = moduleRepository;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    public List<Module> getAll() {
        return moduleRepository.findAll();
    }

    public Module getById(Long id) {
        return moduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not Found"));
    }

    public Module create(ModuleData moduleData) {
        Module module = modelMapper.map(moduleData, Module.class);
        module.setCourse(courseService.getById(moduleData.getCourseId()));
        return moduleRepository.save(module);
    }

    public Module update(Long id, Module module) {
        Module m = getById(id);
        module.setId(id);
        module.setCourse(m.getCourse());
        return moduleRepository.save(module);
    }

    public Module delete(Long id) {
        Module module = getById(id);
        moduleRepository.delete(module);
        return module;
    }
}