package com.org.studentmanagement.service;

import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.dto.StudentInfoDTO;
import com.org.studentmanagement.model.Address;
import com.org.studentmanagement.model.Guardian;
import com.org.studentmanagement.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;


public interface StudentService {

    Page<Student> getAllStudents(PaginationDTO paginationDTO) ;
    Student getStudentById(Long id);

    List<Guardian> getStudentGuardian(Long id);

    List<Address> getStudentAddress(Long id);

    Student saveStudent(StudentInfoDTO studentInfoDTO);
}
