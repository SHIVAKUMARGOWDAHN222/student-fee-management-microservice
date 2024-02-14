package com.org.feeprocess.controller;

import com.org.feeprocess.dto.AdmissionPaymentDTO;
import com.org.feeprocess.service.AdmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/payment")
public class AdmissionController {

    private final AdmissionService admissionService;


    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    @GetMapping("/{studentid}")
    public ResponseEntity<AdmissionPaymentDTO> getStudentById(@PathVariable Long studentid) {
        AdmissionPaymentDTO admissionPayment = admissionService.getAdmissionPaymentInfoByStudentId(studentid);
        return new ResponseEntity<>(admissionPayment,HttpStatus.OK);
    }



}
