package com.org.studentmanagement.service;

import com.org.studentmanagement.dto.CourseDTO;
import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.model.Course;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    Page<Course> getAllCourses(PaginationDTO paginationDTO) ;
    Course getCourseById(Long id);

    List<Course> saveCourses(List<CourseDTO> courses);
}
