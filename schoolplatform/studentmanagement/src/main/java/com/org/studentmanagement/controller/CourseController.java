package com.org.studentmanagement.controller;

import com.org.studentmanagement.dto.CourseDTO;
import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.model.Course;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Page<Course>> getAllCourses(PaginationDTO paginationDTO) {
        return new ResponseEntity<>(courseService.getAllCourses(paginationDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<List<Course>> saveCourses(@RequestBody List<CourseDTO> courses) {
        return new ResponseEntity<>(courseService.saveCourses(courses),HttpStatus.CREATED);
    }


}
