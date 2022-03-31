/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.repository.PrivilegeRepository;
import co.id.mii.mcc63lmsserverside.model.Privilege;
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
public class PrivilegeService {
    
    private PrivilegeRepository privilegeRepository;

    @Autowired
    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }
    
    public List<Privilege> getAll() {
        return privilegeRepository.findAll();
    }
    
    public Privilege getById(Long id) {
        return privilegeRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Privilege not Found."));
    }

    public Privilege create(Privilege privilege) {
        return privilegeRepository.save(privilege);
    }
    
    public Privilege update(Long id, Privilege privilege) {
        getById(id);
        privilege.setId(id);
        return privilegeRepository.save(privilege);
    }
    
    public Privilege delete(Long id) {
        Privilege privilege = getById(id);
        privilegeRepository.delete(privilege);
        return privilege;
    }
}