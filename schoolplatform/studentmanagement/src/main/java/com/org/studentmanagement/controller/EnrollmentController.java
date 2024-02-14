package com.org.studentmanagement.controller;

import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.dto.StudentInfoDTO;
import com.org.studentmanagement.model.Course;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.model.Student;
import com.org.studentmanagement.service.CourseService;
import com.org.studentmanagement.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/{studentId}/enrollments")
    public ResponseEntity<Page<List<Enrollment>>> getStudentEnrollments(@PathVariable Long studentId, PaginationDTO paginationDTO) {
        return new ResponseEntity<>(enrollmentService.getStudentEnrollments(studentId, paginationDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable Long id) {
        return new ResponseEntity<>(enrollmentService.getEnrollmentById(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<List<Enrollment>> saveEnrollment(@RequestBody @Validated List<EnrollmentDto> enrollmentDto) {
        return new ResponseEntity<>(enrollmentService.saveEnrollments(enrollmentDto),HttpStatus.CREATED);
    }
}
