/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.module;

import java.util.List;
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

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<Module> getAll() {
        return moduleRepository.findAll();
    }
    
    public Module getById(Long id) {
        return moduleRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not Found"));
    }
    
    public Module create(Module module) {
        return moduleRepository.save(module);
    }
    
    public Module update(Long id, Module module) {
        getById(id);
        module.setId(id);
        return moduleRepository.save(module);
    }
    
    public Module delete(Long id) {
        Module module = getById(id);
        moduleRepository.delete(module);
        return module;
    }    
}