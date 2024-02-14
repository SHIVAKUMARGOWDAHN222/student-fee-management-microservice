package com.org.feeprocess.service.impl;

import com.org.feeprocess.dto.AdmissionPaymentDTO;
import com.org.feeprocess.repository.AdmissionRepository;
import com.org.feeprocess.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdmissionServiceImplTest {

    @Mock
    private AdmissionRepository admissionRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private AdmissionServiceImpl admissionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAdmissionPaymentInfoByStudentId() {
        Long studentId = 1L;

        when(admissionRepository.findByStudentId(studentId)).thenReturn(Collections.emptyList());
        when(paymentRepository.findByFeeId(anyLong())).thenReturn(null);

        AdmissionPaymentDTO result = admissionService.getAdmissionPaymentInfoByStudentId(studentId);

        assertEquals(0, result.getAdmissions().size());
        assertEquals(0, result.getPayments().size());
    }
}
