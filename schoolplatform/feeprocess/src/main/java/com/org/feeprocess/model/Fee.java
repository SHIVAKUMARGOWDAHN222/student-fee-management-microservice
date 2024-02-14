package com.org.feeprocess.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Fee")
@Data
public class Fee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fee_id")
    private Long id;

    @Column(name="totalfees",nullable = false)
    private Double totalFees;

    @OneToMany(mappedBy = "fee", cascade = CascadeType.PERSIST)
    private List<Admission> admissions;
}

