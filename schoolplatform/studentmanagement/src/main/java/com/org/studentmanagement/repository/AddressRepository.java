package com.org.studentmanagement.repository;

import com.org.studentmanagement.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

    List<Address> findByStudentId(Long studentId);
}
