package com.org.studentmanagement.eventHandlers;

import com.org.studentmanagement.dto.EnrollmentStatus;
import com.org.studentmanagement.model.Enrollment;
import com.org.studentmanagement.repository.EnrollmentRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KafkaConsumerService {

    private final String KAFKA_TOPIC_CONSUMER = "payment-events";
    private final EnrollmentRepository enrollmentRepository;

    public KafkaConsumerService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }


    @KafkaListener(topics = KAFKA_TOPIC_CONSUMER, groupId = "payment-events-group")
    public void listenEnrollmentEvents(EnrollmentStatus enrollmentStatus) {
        updateEnrollmentStatus(enrollmentStatus);
    }


    @Transactional
    private void updateEnrollmentStatus(EnrollmentStatus enrollmentStatus){
        Optional<Enrollment> enrollment = enrollmentRepository.findById(enrollmentStatus.getEnrollmentId());
        if(enrollment.isPresent()){
            enrollment.get().setStatus(enrollmentStatus.getStatus());
            enrollment.get().setReason(enrollmentStatus.getStatus());
            enrollmentRepository.save(enrollment.get());
        }
    }

}

