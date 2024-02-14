package com.org.feeprocess.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentCostDTO {

    private Long enrollmentId;
    private int joiningYear;
    private Long studentId;
    private Long courseId;
    private Double cost;
    private String status;
    private String reason;

    @Override
    public String toString() {
        return "EnrollmentCostDTO{" +
                "enrollmentId=" + enrollmentId +
                ", joiningYear=" + joiningYear +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                ", cost=" + cost +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
