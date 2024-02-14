package com.org.studentmanagement.dto;

import lombok.Data;

@Data
public class GuardianDTO {

    private Long id;
    private Long studentId;
    private String firstName;
    private String lastName;
    private String relationship;
    private String email;
    private String mobileNumber;
}
