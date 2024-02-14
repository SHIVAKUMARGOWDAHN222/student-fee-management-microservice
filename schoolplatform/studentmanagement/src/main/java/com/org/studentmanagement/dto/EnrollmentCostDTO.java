package com.org.studentmanagement.dto;

import lombok.Data;

@Data
public class EnrollmentCostDTO {

    private Long enrollmentId;
    private int joiningYear;
    private Long studentId;
    private Long courseId;
    private Double cost;
    private String status;
    private String reason;
}
