package com.org.feeprocess.enrollmentprocessor;

import com.org.feeprocess.dto.EnrollmentCostDTO;
import com.org.feeprocess.dto.EnrollmentEventDTO;
import com.org.feeprocess.dto.EnrollmentStatus;
import com.org.feeprocess.model.Admission;
import com.org.feeprocess.model.Fee;
import com.org.feeprocess.model.Payment;
import com.org.feeprocess.repository.FeeRepository;
import com.org.feeprocess.repository.PaymentRepository;
import com.org.feeprocess.util.GlobalConstants;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KafkaConsumerService {

    private final String KAFKA_TOPIC_CONSUMER = "enrollment-events";
    private final String KAFKA_TOPIC_PRODUCER = "payment-events";
    private final FeeRepository feeRepository;

    private KafkaTemplate<String, EnrollmentStatus> kafkaTemplate;

    private final PaymentRepository paymentRepository;

    public KafkaConsumerService(FeeRepository feeRepository, PaymentRepository paymentRepository, KafkaTemplate<String, EnrollmentStatus> kafkaTemplate) {
        this.feeRepository = feeRepository;
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    @KafkaListener(topics = KAFKA_TOPIC_CONSUMER, groupId = "enrollment-events-group")
    public void listenEnrollmentEvents(EnrollmentEventDTO enrollmentEventDTO) {
        System.out.println("Received Kafka message: " + enrollmentEventDTO.getTotalFees());
        System.out.println(enrollmentEventDTO.toString());
        processEnrollments(enrollmentEventDTO);
    }


    @Transactional
    private void processEnrollments(EnrollmentEventDTO enrollmentEventDTO){
        Fee fee = this.convertEnrollEventDTOtoFees(enrollmentEventDTO);
        Fee savedFee = feeRepository.save(fee);

        //after saving Enrollments in Admission table and creating entry in Fee Table with
        //status "PENDING". call third party payment integration tool
        //Based on status update Payment Table and Fee Table status to SUCCESS/ERROR
        //based on transaction SUCCESS OR FAILURE

        Payment payment = this.paymentGatewayIntegration(savedFee);
        payment.setFee(savedFee);
        Payment savedPayment = paymentRepository.save(payment);
        String status = savedPayment.getTransactionStatus();
        List<EnrollmentStatus> enrolList = enrollmentEventDTO.getEnrollments().stream()
                .map(enrol -> enrol.getEnrollmentId())
                .collect(Collectors.toList())
                .stream().map(enrolled -> {
                    EnrollmentStatus enrolledStatus = new EnrollmentStatus(enrolled, status);
                    return enrolledStatus;
                }).collect(Collectors.toList());
        enrolList.stream().map(event ->{
            return kafkaTemplate.send(KAFKA_TOPIC_PRODUCER, event);
        }).collect(Collectors.toList());
    }

    private Fee convertEnrollEventDTOtoFees(EnrollmentEventDTO enrollmentEventDTO){
        Fee fee = new Fee();
        fee.setTotalFees(enrollmentEventDTO.getTotalFees());
        List<Admission> admissions = enrollmentEventDTO.getEnrollments().stream()
                .map(admission -> {
                    Admission admit = this.convertEnrollmentCostDTOtoAdmission(admission);
                    return admit;
                }).collect(Collectors.toList());

        admissions.forEach(admission -> admission.setFee(fee));
        fee.setAdmissions(admissions);
        return fee;
    }

    private Admission convertEnrollmentCostDTOtoAdmission(EnrollmentCostDTO enrollmentCostDTO){
        Admission admission = new Admission();
        admission.setEnrollmentId(enrollmentCostDTO.getEnrollmentId());
        admission.setCost(enrollmentCostDTO.getCost());
        admission.setStudentId(enrollmentCostDTO.getStudentId());
        admission.setCourseId(enrollmentCostDTO.getCourseId());
        admission.setJoiningYear(enrollmentCostDTO.getJoiningYear());
        return admission;
    }

    private Payment paymentGatewayIntegration(Fee fee){
        Payment payment = new Payment();
        // Create a  dummy Random referenceNumber to mock payment integration
        long randomLong = 10L+1+fee.getId();
        payment.setTransactionStatus(GlobalConstants.STATUS_SUCCESS);
        payment.setReferenceNumber(randomLong);
        payment.setAmount(fee.getTotalFees());
        payment.setTransactionDateTime(new Date());
        return payment;
    }


}

