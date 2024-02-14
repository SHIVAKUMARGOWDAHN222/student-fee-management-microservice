package com.org.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String name;

    private String description;

    private Double cost;

    @Column(name = "students_limit")
    private int studentLimit;

/*    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<Enrollment> enrollments;*/

}
