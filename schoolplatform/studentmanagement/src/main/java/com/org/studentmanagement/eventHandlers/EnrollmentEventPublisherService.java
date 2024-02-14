package com.org.studentmanagement.eventHandlers;

import com.org.studentmanagement.dto.EnrollmentCostDTO;
import com.org.studentmanagement.dto.EnrollmentDto;
import com.org.studentmanagement.dto.EnrollmentEventDTO;
import com.org.studentmanagement.model.Course;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.repository.CourseRepository;
import com.org.studentmanagement.util.GlobalConstants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EnrollmentEventPublisherService {

    private KafkaTemplate<String, EnrollmentEventDTO> kafkaTemplate;
    private final String KAFKA_TOPIC = "enrollment-events";

    private final CourseRepository courseRepository;

    public EnrollmentEventPublisherService(CourseRepository courseRepository, KafkaTemplate kafkaTemplate) {
        this.courseRepository = courseRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enrolCourseCreatedEvent(List<Enrollment> enrollmentEvents){
        EnrollmentEventDTO enrollmentEventDTO = this.convertEnrolmentToEnrollmentEventDTO(enrollmentEvents);
        kafkaTemplate.send(KAFKA_TOPIC,enrollmentEventDTO );
        System.out.println("Kafka msg ---------->"+ enrollmentEventDTO.getTotalFees());
    }


    private EnrollmentEventDTO convertEnrolmentToEnrollmentEventDTO(List<Enrollment> enrollmentEvents){
        EnrollmentEventDTO dto = new EnrollmentEventDTO();

        // Get student ID from any enrollment
        dto.setStudentId(enrollmentEvents.get(0).getStudent().getId());

        // Initialize list for course IDs
        List<String> enrolmentIds = new ArrayList<>();

        for (Enrollment enrollment : enrollmentEvents) {
            enrolmentIds.add(enrollment.getId().toString());
        }

        List<Optional<Course>> enrolledCourses = enrollmentEvents.stream().map(
                enrol -> {
                    Optional<Course> courses = courseRepository.findById(enrol.getCourseId());
                    return courses;
                }
        ).collect(Collectors.toList());


        double totalCost = enrolledCourses.stream().filter(courseenrol->courseenrol.isPresent())
                .map(courseenrol->courseenrol.get().getCost())
                .collect(Collectors.summarizingDouble(Double::doubleValue))
                        .getSum();

        totalCost = BigDecimal.valueOf(totalCost).setScale(2, RoundingMode.HALF_UP).doubleValue();

       // dto.setEnrolmentIds(enrolmentIds);

        Map<Long, Double> courseCostMap = enrolledCourses.stream()
                .map(Optional::orElseThrow) // or .map(opt -> opt.orElseThrow())
                .filter(course -> course != null)
                .collect(Collectors.toMap(Course::getId, Course::getCost));

        List<EnrollmentCostDTO> enrollments = enrollmentEvents.stream()
                .map(enrol -> {
                    EnrollmentCostDTO enroldto = this.convertEnrollmentToDTO(enrol, courseCostMap);
                    return enroldto;
                }).collect(Collectors.toList());

        dto.setEnrollments(enrollments);
        dto.setTotalFees(totalCost);
        dto.setEnrollmentDate(new Date());
        return dto;
    }


    private EnrollmentCostDTO convertEnrollmentToDTO(Enrollment enrollment, Map<Long, Double> courseCostMap){
        EnrollmentCostDTO enrollmentDto = new EnrollmentCostDTO();
        enrollmentDto.setJoiningYear(enrollment.getJoiningYear());
        enrollmentDto.setCourseId(enrollment.getCourseId());
        enrollmentDto.setStatus(GlobalConstants.STATUS_PENDING);
        enrollmentDto.setReason(GlobalConstants.STATUS_REASON_INITIATED);
        enrollmentDto.setStudentId(enrollment.getStudent().getId());
        enrollmentDto.setEnrollmentId(enrollment.getId());
        enrollmentDto.setCost(courseCostMap.get(enrollment.getCourseId()));
        return enrollmentDto;
    }


}
