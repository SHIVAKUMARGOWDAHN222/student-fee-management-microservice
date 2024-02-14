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
@Table(name = "Address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

/*    @Column(name = "student_id")
    private Long studentId;*/

    private String street;

    private String city;

    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

}
