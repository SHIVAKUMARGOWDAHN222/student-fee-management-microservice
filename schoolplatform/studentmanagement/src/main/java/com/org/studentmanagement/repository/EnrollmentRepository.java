package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
