package com.org.feeprocess.dto;

import com.org.feeprocess.model.Fee;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class AdmissionDTO {

    private Long admissionId;
    private Long enrollmentId;
    private Long feeId;
    private Long studentId;
    private Long courseId;
    private Integer joiningYear;
    private Double cost;

}
