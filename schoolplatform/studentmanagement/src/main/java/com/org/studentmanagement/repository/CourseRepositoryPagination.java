package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepositoryPagination extends PagingAndSortingRepository<Course, Long> {
    Course findById(Long id);
}
