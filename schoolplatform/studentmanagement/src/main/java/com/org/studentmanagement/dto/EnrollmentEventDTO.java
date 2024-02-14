package com.org.studentmanagement.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EnrollmentEventDTO {

    private Long StudentId;
    private List<EnrollmentCostDTO> enrollments;
    //private List<String> enrolmentIds;
    private Date enrollmentDate;
    private Double totalFees;
    private String status;

}
