/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.controller;

import co.id.mii.mcc63lmsserverside.model.Dto.ModuleDto;
import co.id.mii.mcc63lmsserverside.model.Module;
import co.id.mii.mcc63lmsserverside.service.ModuleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Agung
 */
@RestController
@RequestMapping("/module")
public class ModuleController {

    private ModuleService moduleService;

    @Autowired
    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<List<Module>> getAll() {
        return new ResponseEntity(moduleService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Module> getById(@PathVariable Long id) {
        return new ResponseEntity(moduleService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Module> create(@RequestBody ModuleDto moduleDto) {
        return new ResponseEntity(moduleService.create(moduleDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Module> update(@PathVariable Long id, @RequestBody Module module) {
        return new ResponseEntity(moduleService.update(id, module), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Module> delete(Long id) {
        return new ResponseEntity(moduleService.delete(id), HttpStatus.OK);
    }
}
