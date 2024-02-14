package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByIdAndEnrollmentsCourseIdIn(Long studentId, List<Long> courseIds);
}
