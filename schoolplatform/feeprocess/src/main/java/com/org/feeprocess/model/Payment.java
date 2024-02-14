package com.org.feeprocess.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Payment")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fee_id", nullable = false)
    private Fee fee;

    @Column(name="transaction_date_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDateTime;

    @Column(nullable = false)
    private Double amount;

    @Column(name="transaction_status", nullable = false)
    private String transactionStatus;

    @Column(name = "reference_number")
    private Long referenceNumber;

}

