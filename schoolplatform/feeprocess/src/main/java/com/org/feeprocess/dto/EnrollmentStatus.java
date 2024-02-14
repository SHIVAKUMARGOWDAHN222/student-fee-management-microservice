package com.org.feeprocess.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentStatus {

    private Long enrollmentId;
    private String status;
}
