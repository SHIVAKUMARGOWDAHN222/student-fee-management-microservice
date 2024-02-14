package com.org.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Guardian")
public class Guardian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guardian_id")
    private Long id;

/*    @Column(name = "student_id")
    private Long studentId;*/

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String relationship;

    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;
}
