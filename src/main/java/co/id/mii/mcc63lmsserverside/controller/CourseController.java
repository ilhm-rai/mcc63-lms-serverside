/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.controller;

import co.id.mii.mcc63lmsserverside.model.Dto.CourseDto;
import co.id.mii.mcc63lmsserverside.service.CourseService;
import co.id.mii.mcc63lmsserverside.model.Course;
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
@RequestMapping("/course")
public class CourseController {
    
    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        return new ResponseEntity(courseService.getAll(), HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable Long id) {
        return new ResponseEntity(courseService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody CourseDto courseDto) {
        return new ResponseEntity(courseService.create(courseDto), HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody CourseDto courseDto) {
        return new ResponseEntity(courseService.update(id, courseDto), HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Course> delete(@PathVariable Long id) {
        return new ResponseEntity(courseService.delete(id), HttpStatus.OK);
    }
}
