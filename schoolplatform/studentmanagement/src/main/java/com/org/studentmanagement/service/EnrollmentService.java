package com.org.studentmanagement.service;

import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.dto.StudentInfoDTO;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EnrollmentService {

    Page<List<Enrollment>> getStudentEnrollments(Long studentId, PaginationDTO paginationDTO) ;
    Enrollment getEnrollmentById(Long id);

    List<Enrollment> saveEnrollments(List<EnrollmentDto> enrollmentDtos);
}
