package com.org.feeprocess.service.impl;

import com.org.feeprocess.dto.AdmissionDTO;
import com.org.feeprocess.dto.AdmissionPaymentDTO;
import com.org.feeprocess.dto.PaymentDTO;
import com.org.feeprocess.model.Admission;
import com.org.feeprocess.model.Payment;
import com.org.feeprocess.repository.AdmissionRepository;
import com.org.feeprocess.repository.PaymentRepository;
import com.org.feeprocess.service.AdmissionService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdmissionServiceImpl implements AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final PaymentRepository paymentRepository;

    public AdmissionServiceImpl(AdmissionRepository admissionRepository, PaymentRepository paymentRepository) {
        this.admissionRepository = admissionRepository;
        this.paymentRepository = paymentRepository;
    }


    @Override
    public AdmissionPaymentDTO getAdmissionPaymentInfoByStudentId(Long id) {
        List<Admission> admissions = admissionRepository.findByStudentId(id);

        List<Long> feeidlist = admissions.stream()
                .map(admission -> admission.getId())
                .distinct().collect(Collectors.toList());
        List<Payment> paymentList = feeidlist.stream().map(admission -> {
            Payment payments = paymentRepository.findByFeeId(admission);
            return payments;
        }).collect(Collectors.toList());
        AdmissionPaymentDTO admissionPaymentDTO = new AdmissionPaymentDTO();
        List<AdmissionDTO> admDTO = admissions.stream().map(admission -> {
            return convertToAdmissionDTO(admission);
        }).collect(Collectors.toList());
        List<PaymentDTO> paymentDTO = paymentList.stream().map(payment -> {
            return convertToPaymentDTO(payment);
        }).collect(Collectors.toList());
        admissionPaymentDTO.setAdmissions(admDTO);
        admissionPaymentDTO.setPayments(paymentDTO);
        return admissionPaymentDTO;
    }


    private PaymentDTO convertToPaymentDTO(Payment payment){
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentid(payment.getId());
        paymentDTO.setFeeId(paymentDTO.getFeeId());
        paymentDTO.setTransactionDateTime(payment.getTransactionDateTime());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setTransactionStatus(payment.getTransactionStatus());
        paymentDTO.setReferenceNumber(payment.getReferenceNumber());
        return paymentDTO;
    }

    private AdmissionDTO convertToAdmissionDTO(Admission admission){
        AdmissionDTO admissionDTO = new AdmissionDTO();
        admissionDTO.setAdmissionId(admission.getId());
        admissionDTO.setEnrollmentId(admission.getEnrollmentId());
        admissionDTO.setFeeId(admission.getFee().getId());
        admissionDTO.setStudentId(admission.getStudentId());
        admissionDTO.setCourseId(admission.getCourseId());
        admissionDTO.setJoiningYear(admission.getJoiningYear());
        admissionDTO.setCost(admission.getCost());
        return admissionDTO;
    }
}
