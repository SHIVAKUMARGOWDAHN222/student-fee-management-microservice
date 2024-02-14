package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Student;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryPagination extends PagingAndSortingRepository<Student,Long> {

    Student findById(Long studentId);

    Student save(Student student);
}
