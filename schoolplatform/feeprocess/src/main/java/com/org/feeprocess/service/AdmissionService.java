package com.org.feeprocess.service;

import com.org.feeprocess.dto.AdmissionPaymentDTO;


public interface AdmissionService {

    AdmissionPaymentDTO getAdmissionPaymentInfoByStudentId(Long id);
}
