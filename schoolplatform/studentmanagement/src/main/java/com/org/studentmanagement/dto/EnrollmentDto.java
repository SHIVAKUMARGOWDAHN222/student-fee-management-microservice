package com.org.studentmanagement.dto;

import lombok.Data;

@Data
public class EnrollmentDto {


    private int joiningYear;
    private Long studentId;
    private Long courseId;
    private Double cost;

}
