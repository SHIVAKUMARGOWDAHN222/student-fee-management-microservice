package com.org.studentmanagement.dto;

import com.org.studentmanagement.model.Address;
import com.org.studentmanagement.model.Guardian;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
public class StudentInfoDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String schoolName;
    private int grade;
    private String email;
    private String mobileNumber;
    private List<Address> addresses;
    private List<Guardian> guardians;
}
