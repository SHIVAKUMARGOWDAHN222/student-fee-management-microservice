package com.org.feeprocess.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEventDTO {

    private Long StudentId;
    private List<EnrollmentCostDTO> enrollments;
    //private List<String> enrolmentIds;
    private Date enrollmentDate;
    private Double totalFees;
    private String status;

    @Override
    public String toString() {
        return "EnrollmentEventDTO{" +
                "StudentId=" + StudentId +
                ", enrollments=" + enrollments +
                ", enrollmentDate=" + enrollmentDate +
                ", totalFees=" + totalFees +
                ", status='" + status + '\'' +
                '}';
    }
}
