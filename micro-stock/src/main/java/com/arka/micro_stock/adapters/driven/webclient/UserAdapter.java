package com.arka.micro_stock.adapters.driven.webclient;

import com.arka.micro_stock.adapters.driven.security.AuthenticationAdapter;
import com.arka.micro_stock.domain.model.UserModel;
import com.arka.micro_stock.domain.spi.IAuthenticationPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
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
public class UserAdapter implements IUserPersistencePort {

    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final WebClient userWebClient;
    private static final String USER_SERVICE = "userService";

    @Override
    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "fallbackExists")
    @Retry(name = USER_SERVICE)
    @Bulkhead(name = USER_SERVICE)
    public Mono<Boolean> existsByIdAndValidRole(Long id, String role) {
        return authenticationPersistencePort.getAuthorizationHeader()
                .flatMap(authHeader ->
                        userWebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/exists/{id}/{role}")
                                        .build(id, role))
                                .header(HttpHeaders.AUTHORIZATION, authHeader)
                                .retrieve()
                                .bodyToMono(Boolean.class)
                )
                .doOnError(error -> log.error("Error calling user service: {}", error.getMessage()));
    }

    @Override
    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "fallbackFindByEmail")
    @Retry(name = USER_SERVICE)
    @Bulkhead(name = USER_SERVICE)
    public Mono<UserModel> findByEmail(String email) {
        return authenticationPersistencePort.getAuthorizationHeader()
                .flatMap(authHeader ->
                        userWebClient.get()
                                .uri(uriBuilder -> uriBuilder
                                        .path("/email/{email}")
                                        .build(email))
                                .header(HttpHeaders.AUTHORIZATION, authHeader)
                                .retrieve()
                                .bodyToMono(UserModel.class)
                )
                .doOnError(error -> log.error("Error calling user service: {}", error.getMessage()));
    }


    public Mono<Boolean> fallbackExists(Long id, String role, Exception ex) {
        log.warn("Fallback method called for existsByIdAndValidRole. ID: {}, Role: {}, Error: {}",
                id, role, ex.getMessage());
        return Mono.just(false);
    }

    public Mono<UserModel> fallbackFindByEmail(String email, Throwable ex) {
        log.warn("Fallback method called for findByEmail. Email: {}, Error: {}", email, ex.getMessage());
        return Mono.empty();
    }

}
