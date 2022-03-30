/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.course;

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
public class CourseService {
    
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public List<Course> getAll() {
        return courseRepository.findAll();
    }
    
    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(() 
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not Found."));
    }
    
    public Course create(Course course) {
        return courseRepository.save(course);
    }
    
    public Course update(Long id, Course course) {
        getById(id);
        course.setId(id);
        return courseRepository.save(course);
    }
    
    public Course delete(Long id) {
        Course course = getById(id);
        courseRepository.delete(course);
        return course;
    }
}