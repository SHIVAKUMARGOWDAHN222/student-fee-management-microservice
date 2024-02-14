package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Guardian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Long> {

    List<Guardian> findByStudentId(Long studentId);
}
