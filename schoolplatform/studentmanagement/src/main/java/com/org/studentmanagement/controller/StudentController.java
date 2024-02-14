package com.org.studentmanagement.controller;

import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.dto.StudentInfoDTO;
import com.org.studentmanagement.model.Address;
import com.org.studentmanagement.model.Guardian;
import com.org.studentmanagement.model.Student;
import com.org.studentmanagement.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<Student>> getAllStudents( PaginationDTO paginationDTO) {
        return new ResponseEntity<>(studentService.getAllStudents(paginationDTO), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentById(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/guardian")
    public ResponseEntity<List<Guardian>> getStudentGuardian(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentGuardian(id),HttpStatus.OK);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<List<Address>> getStudentAddress(@PathVariable Long id) {
        return new ResponseEntity<>(studentService.getStudentAddress(id),HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Student> saveStudent(@RequestBody @Validated StudentInfoDTO studentInfoDTO) {
        return new ResponseEntity<>(studentService.saveStudent(studentInfoDTO),HttpStatus.CREATED);
    }
}

