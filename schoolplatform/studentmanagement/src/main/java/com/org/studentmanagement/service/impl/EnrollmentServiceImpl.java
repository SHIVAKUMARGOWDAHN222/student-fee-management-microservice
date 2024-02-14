package com.org.studentmanagement.service.impl;

import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.PaginationDTO;
import com.org.studentmanagement.eventHandlers.EnrollmentEventPublisherService;
import com.org.studentmanagement.exception.EnrollmentException;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.model.Student;
import com.org.studentmanagement.repository.EnrollmentRepository;
import com.org.studentmanagement.repository.EnrollmentRepositoryPagination;
import com.org.studentmanagement.repository.StudentRepository;
import com.org.studentmanagement.service.EnrollmentService;
import com.org.studentmanagement.util.GlobalConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepositoryPagination enrollmentRepositoryPagination;
    private final StudentRepository studentRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentEventPublisherService eventPublisherService;



    public EnrollmentServiceImpl(EnrollmentRepositoryPagination enrollmentRepositoryPagination, StudentRepository studentRepository, EnrollmentRepository enrollmentRepository, EnrollmentEventPublisherService eventPublisherService){
        this.enrollmentRepositoryPagination = enrollmentRepositoryPagination;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.eventPublisherService = eventPublisherService;
    }
    @Override
    public Page<List<Enrollment>> getStudentEnrollments(Long studentId, PaginationDTO paginationDTO) {
        Sort sort = Sort.by(paginationDTO.getSortDirection(), paginationDTO.getSortBy());
        Pageable pageable = PageRequest.of(paginationDTO.getPageNumber(),
                paginationDTO.getPageSize(), sort);
        return enrollmentRepositoryPagination.findByStudentId(studentId, pageable);
    }

    @Override
    public Enrollment getEnrollmentById(Long id) {
        return enrollmentRepositoryPagination.findById(id);
    }

    @Transactional
    public List<Enrollment> saveEnrollments(List<EnrollmentDto> newEnrollments) {
        Student savedStudent = new Student();
        List<Enrollment> enrolments= new ArrayList<>();
        if (newEnrollments.size() > 0) {
            Long studentId = newEnrollments.get(0).getStudentId();
            Optional<Student> optionalStudent = studentRepository.findById(studentId);


            if (optionalStudent.isPresent()) {
                Student student = optionalStudent.get();

                // Check for duplicate enrollments
                List<Long> courseIds = newEnrollments.stream()
                        .map(enrollment -> enrollment.getCourseId())
                        .collect(Collectors.toList());

                List<Long> existingEnrolledCourseIds = studentRepository
                        .findById(studentId)
                        .stream().flatMap(studentData -> studentData
                                .getEnrollments().stream()
                                .mapToLong(courseData -> courseData.getCourseId()).boxed())
                        .collect(Collectors.toList());

                List<Long> duplicateCourseIds = courseIds.stream()
                        .filter(existingEnrolledCourseIds::contains)
                        .collect(Collectors.toList());

                if (!duplicateCourseIds.isEmpty()) {
                    throw new EnrollmentException("Student already enrolled for the following courses: " + duplicateCourseIds);
                }

                List<Enrollment> enrollments = newEnrollments.stream()
                        .map(enrollmentDto -> {
                            Enrollment newEnrollment = convertStudentInfoToStudent(enrollmentDto);
                            newEnrollment.setStudent(student);
                            return newEnrollment;
                        })
                        .collect(Collectors.toList());
                 enrolments = enrollmentRepository.saveAll(enrollments);
            } else {
                throw new EnrollmentException("Student not found with ID: " + studentId);
            }

        }
        this.eventPublisherService.enrolCourseCreatedEvent(enrolments);
        return enrolments;
    }

    private Enrollment convertStudentInfoToStudent(EnrollmentDto enrollmentDto){
       Enrollment enrollment = new Enrollment();
       enrollment.setJoiningYear(enrollmentDto.getJoiningYear());
       enrollment.setCourseId(enrollmentDto.getCourseId());
       enrollment.setStatus(GlobalConstants.STATUS_PENDING);
       enrollment.setReason(GlobalConstants.STATUS_REASON_INITIATED);
       return enrollment;
    }
}
