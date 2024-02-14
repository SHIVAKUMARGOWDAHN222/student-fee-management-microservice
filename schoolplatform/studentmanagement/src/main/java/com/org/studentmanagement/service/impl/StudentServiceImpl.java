package com.org.studentmanagement.service.impl;

import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.dto.StudentInfoDTO;
import com.org.studentmanagement.model.Address;
import com.org.studentmanagement.model.Guardian;
import com.org.studentmanagement.model.Student;
import com.org.studentmanagement.repository.AddressRepository;
import com.org.studentmanagement.repository.GuardianRepository;
import com.org.studentmanagement.repository.StudentRepository;
import com.org.studentmanagement.repository.StudentRepositoryPagination;
import com.org.studentmanagement.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepositoryPagination studentRepositoryPagination;
    private final GuardianRepository guardianRepository;

    private final AddressRepository addressRepository;

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepositoryPagination studentRepositoryPagination, GuardianRepository guardianRepository, AddressRepository addressRepository, StudentRepository studentRepository) {
        this.studentRepositoryPagination = studentRepositoryPagination;
        this.guardianRepository = guardianRepository;
        this.addressRepository = addressRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<Student> getAllStudents(PaginationDTO paginationDTO) {
        Sort sort = Sort.by(paginationDTO.getSortDirection(), paginationDTO.getSortBy());
        Pageable pageable = PageRequest.of(paginationDTO.getPageNumber(),
                paginationDTO.getPageSize(), sort);
        return studentRepositoryPagination.findAll(pageable);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepositoryPagination.findById(id);
    }

    @Override
    public List<Guardian> getStudentGuardian(Long id) {
        return guardianRepository.findByStudentId(id);
    }

    @Override
    public List<Address> getStudentAddress(Long id) {
        return addressRepository.findByStudentId(id);
    }

    @Transactional
    public Student saveStudent(StudentInfoDTO studentInfoDTO) {
        Student student = convertStudentInfoToStudent(studentInfoDTO);
        for (Address address : student.getAddresses()) {
            address.setStudent(student);
        }

        for (Guardian guardian : student.getGuardians()) {
            guardian.setStudent(student);
        }

        Student savedStudent = studentRepository.save(student);

        // Addresses and Guardians will be saved due to CascadeType.PERSIST
        return savedStudent;

    }

    private Student convertStudentInfoToStudent(StudentInfoDTO studentInfoDTO){
        Student student = new Student();
        student.setFirstName(studentInfoDTO.getFirstName());
        student.setLastName(studentInfoDTO.getLastName());
        student.setSchoolName(studentInfoDTO.getSchoolName());
        student.setGrade(studentInfoDTO.getGrade());
        student.setEmail(studentInfoDTO.getEmail());
        student.setMobileNumber(studentInfoDTO.getMobileNumber());
        student.setAddresses(studentInfoDTO.getAddresses());
        student.setGuardians(studentInfoDTO.getGuardians());
        return student;
    }
}