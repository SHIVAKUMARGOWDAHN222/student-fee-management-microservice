package com.org.feeprocess.dto;

import com.org.feeprocess.model.Admission;
import com.org.feeprocess.model.Payment;
import lombok.Data;

import java.util.List;
@Data
public class AdmissionPaymentDTO {

    private List<AdmissionDTO> admissions;
    private List<PaymentDTO> payments;
}
