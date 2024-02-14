package com.org.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "school_name")
    private String schoolName;

    private int grade;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "student")
    private List<Address> addresses;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "student")
    private List<Guardian> guardians;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "student")
    private List<Enrollment> enrollments;
}
