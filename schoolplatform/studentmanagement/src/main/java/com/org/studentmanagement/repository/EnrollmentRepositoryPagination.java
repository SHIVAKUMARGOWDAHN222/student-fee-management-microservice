package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepositoryPagination extends PagingAndSortingRepository<Enrollment, Long> {
    Page<List<Enrollment>> findByStudentId(Long studentId, Pageable pageable);
    Enrollment findById(Long id);
}
