/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package co.id.mii.mcc63lmsserverside.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import co.id.mii.mcc63lmsserverside.controller.CourseController;
import co.id.mii.mcc63lmsserverside.model.Course;
import co.id.mii.mcc63lmsserverside.model.dto.CourseData;
import co.id.mii.mcc63lmsserverside.repository.CourseRepository;
import co.id.mii.mcc63lmsserverside.util.StorageService;
import net.bytebuddy.utility.RandomString;

/**
 *
 * @author Agung
 */
@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserService userService,
            CategoryService categoryService,
            ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    public List<Course> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(c -> new Course(
                        c.getId(),
                        c.getTitle(),
                        c.getDescription(),
                        c.getPrice(),
                        c.getIsActive(),
                        toUri(c.getCourseImage()),
                        c.getUser(),
                        c.getCategory(),
                        c.getModules(),
                        c.getEnrollments()))
                .collect(Collectors.toList());
    }

    public Course getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Course not Found."));
        course.setCourseImage(toUri(course.getCourseImage()));
        return course;
    }

    public List<Course> myCourse(Long userId) {
        return courseRepository.findMyCourse(userId).orElse(new ArrayList<>());
    }

    public Course create(CourseData courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);

        String courseImage = RandomString.make(20) + "."
                + FilenameUtils.getExtension(courseDto.getCourseImage().getOriginalFilename());

        StorageService.store("upload/course", courseImage, courseDto.getCourseImage());

        course.setIsActive(false);
        course.setCourseImage(courseImage);
        course.setUser(userService.getUserById(courseDto.getUserId()));
        course.setCategory(categoryService.getById(courseDto.getCategoryId()));
        return courseRepository.save(course);
    }

    public Course update(Long id, Course course) {
        Course c = getById(id);
        course.setId(id);
        course.setUser(c.getUser());
        course.setCategory(c.getCategory());
        return courseRepository.save(course);
    }

    public Course delete(Long id) {
        Course course = getById(id);
        courseRepository.delete(course);
        return course;
    }

    private String toUri(String filename) {
        return MvcUriComponentsBuilder
                .fromMethodName(CourseController.class, "getFile", filename)
                .build().toUri().toString();
    }
}
