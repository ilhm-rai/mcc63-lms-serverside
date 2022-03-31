/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import co.id.mii.mcc63lmsserverside.model.Dto.CourseDto;
import co.id.mii.mcc63lmsserverside.repository.CourseRepository;
import co.id.mii.mcc63lmsserverside.model.Course;
import co.id.mii.mcc63lmsserverside.service.CategoryService;
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

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserService userService, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not Found."));
    }

    public Course create(CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setPrice(courseDto.getPrice());
        course.setIsActive(false);
        course.setUser(userService.getUserById(courseDto.getUserId()));
        course.setCategory(categoryService.getById(courseDto.getCategoryId()));
        return courseRepository.save(course);
    }

    public Course update(Long id, CourseDto courseDto) {
        Course course = getById(id);
        course.setId(id);
        course.setUser(userService.getUserById(courseDto.getUserId()));
        course.setCategory(categoryService.getById(courseDto.getCategoryId()));
        return courseRepository.save(course);
    }

    public Course delete(Long id) {
        Course course = getById(id);
        courseRepository.delete(course);
        return course;
    }
}
