/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.model.Dto.EnrollmentDto;
import co.id.mii.mcc63lmsserverside.repository.EnrollmentRepository;
import co.id.mii.mcc63lmsserverside.model.Enrollment;
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
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserService userService;
    private final CourseService courseService;
    private final ModelMapper modelMapper;

    @Autowired
    public EnrollmentService(EnrollmentRepository enrollmentRepository, UserService userService, CourseService courseService, ModelMapper modelMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.userService = userService;
        this.courseService = courseService;
        this.modelMapper = modelMapper;
    }

    public List<Enrollment> getAll() {
        return enrollmentRepository.findAll();
    }

    public Enrollment getById(Long id) {
        return enrollmentRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not Found"));
    }

    public Enrollment create(EnrollmentDto enrollmentDto) {
        Enrollment enrollment = modelMapper.map(enrollmentDto, Enrollment.class);
        enrollment.setPayment_status(false);
        enrollment.setUser(userService.getUserById(enrollmentDto.getUserId()));
        enrollment.setCourse(courseService.getById(enrollmentDto.getCourseId()));
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment update(Long id, Enrollment enrollment) {
        Enrollment e = getById(id);
        enrollment.setId(id);
        enrollment.setUser(e.getUser());
        enrollment.setCourse(e.getCourse());
        return enrollmentRepository.save(enrollment);
    }

    public Enrollment delete(Long id) {
        Enrollment enrollment = getById(id);
        enrollmentRepository.delete(enrollment);
        return enrollment;
    }
}
