package com.org.studentmanagement.dto;

import lombok.Data;

@Data
public class CourseDTO {

    private String name;
    private String description;
    private Double cost;
    private int studentLimit;

}
