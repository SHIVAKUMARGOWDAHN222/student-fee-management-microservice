package com.org.feeprocess.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Admissions")
@Data
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admission_id")
    private Long id;

    @Column(name="enrollment_id", nullable = false)
    private Long enrollmentId;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name="course_id", nullable = false)
    private Long courseId;

    @Column(name="joining_year", nullable = false)
    private Integer joiningYear;

    @Column(nullable = false)
    private Double cost;

}

