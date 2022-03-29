/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.enrollment;

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
public class EnrollmentService {
    
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }
    
    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }
    
    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not Found"));
    }
    
    public Enrollment create(Enrollment enrollment) {
        return enrollmentRepository.save(enrollment);
    }
    
    public Enrollment update(Long id, Enrollment enrollment) {
        getById(id);
        enrollment.setId(id);
        return enrollmentRepository.save(enrollment);
    }
    
    public Enrollment delete(Long id) {
        Enrollment enrollment = getById(id);
        enrollmentRepository.delete(enrollment);
        return enrollment;
    }    
}