package com.org.feeprocess.repository;

import com.org.feeprocess.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {
    List<Admission> findByStudentId(Long studentId);
}
