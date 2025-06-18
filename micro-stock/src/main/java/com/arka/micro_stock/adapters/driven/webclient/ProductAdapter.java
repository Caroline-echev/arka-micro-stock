package com.arka.micro_stock.adapters.driven.webclient;

import com.arka.micro_stock.adapters.driven.security.AuthenticationAdapter;
import com.arka.micro_stock.domain.spi.IAuthenticationPersistencePort;
import com.arka.micro_stock.domain.spi.IProductPersistencePort;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductAdapter implements IProductPersistencePort {

    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final WebClient catalogWebClient;
    private static final String PRODUCT_SERVICE = "productService";

    @Override
    @CircuitBreaker(name = PRODUCT_SERVICE, fallbackMethod = "fallbackExists")
    @Retry(name = PRODUCT_SERVICE)
    @Bulkhead(name = PRODUCT_SERVICE)
    public Mono<Boolean> existsById(Long id) {
        return authenticationPersistencePort.getAuthorizationHeader()
                .flatMap(authHeader ->
                        catalogWebClient.get()
                                .uri("/exists/{id}", id)
                                .header(HttpHeaders.AUTHORIZATION, authHeader)
                                .retrieve()
                                .bodyToMono(Boolean.class)
                )
                .doOnError(error -> log.error("Error calling product service: {}", error.getMessage()));
    }

    public Mono<Boolean> fallbackExists(Long id, Exception ex) {
        log.warn("Fallback method called for existsById. ID: {}, Error: {}", id, ex.getMessage());
        return Mono.just(false);
    }
}
