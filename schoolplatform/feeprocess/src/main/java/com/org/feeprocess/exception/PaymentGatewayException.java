package com.org.feeprocess.exception;

public class PaymentGatewayException extends RuntimeException{

    public PaymentGatewayException(String message) {
        super(message);
    }
}
