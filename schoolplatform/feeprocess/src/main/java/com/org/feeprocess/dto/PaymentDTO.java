package com.org.feeprocess.dto;

import com.org.feeprocess.model.Fee;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDTO {

    private Long paymentid;
    private Long feeId;
    private Date transactionDateTime;
    private Double amount;
    private String transactionStatus;
    private Long referenceNumber;

}
