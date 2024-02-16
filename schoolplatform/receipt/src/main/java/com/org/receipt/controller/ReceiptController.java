package com.org.receipt.controller;

import com.org.receipt.service.ReceiptService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/receipt")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{studentid}")
    @RateLimiter(name = "squareLimit", fallbackMethod = "fallBackMethod")
    public Mono<ResponseEntity<String>> getDataFromMicroservices(@PathVariable Long studentid) {
        return receiptService.getDataFromMicroservices(studentid)
                .map(result -> ResponseEntity.ok().body(result))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    public ResponseEntity<String> fallBackMethod(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("RateLimiter does not permit further calls. Please try after sometime");
    }
}

