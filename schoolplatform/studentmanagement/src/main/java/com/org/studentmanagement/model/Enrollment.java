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
@Table(name = "Enrollment")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Long id;

    @Column(name = "joining_year")
    private int joiningYear;

/*    @Column(name = "student_id")
    private Long studentId;*/

    @Column(name = "course_id")
    private Long courseId;

    private String status;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

/*    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course;*/

}
