package com.org.receipt.service.ServiceImpl;

import com.org.receipt.Util.GlobalConstants;
import com.org.receipt.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReceiptServiceImpl implements ReceiptService {

    private final WebClient webClientForService1;
    private final WebClient webClientForService2;

    @Autowired
    public ReceiptServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientForService1 = webClientBuilder.baseUrl(GlobalConstants.FEE_PROCESS_SERVICE_URL).build();
        this.webClientForService2 = webClientBuilder.baseUrl(GlobalConstants.STUDENT_MANAGEMENT_SERVICE_URL).build();
    }

    public Mono<String> getDataFromMicroservices(Long studentId) {
        return webClientForService1.get()
                .uri("/{id}", studentId)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> handleServiceError("Service1", error))
                .flatMap(dataFromService1 -> {
                    return webClientForService2.get()
                            .uri("/{id}",studentId)
                            .retrieve()
                            .bodyToMono(String.class)
                            .onErrorResume(error -> handleServiceError("Service2", error))
                            .map(dataFromService2 -> {
                                return "Combined Result: " + dataFromService1 + ", " + dataFromService2;
                            });
                });
    }

    private Mono<String> handleServiceError(String serviceName, Throwable error) {
        return Mono.just("Error occurred in " + serviceName + ": " + error.getMessage());
    }
}

