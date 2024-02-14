package com.org.receipt.service;

import reactor.core.publisher.Mono;

public interface ReceiptService {
    Mono<String> getDataFromMicroservices(Long StudentId);

}


