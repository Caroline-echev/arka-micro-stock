package com.arka.micro_stock.domain.spi;

import reactor.core.publisher.Mono;

public interface IAuthenticationPersistencePort {
    Mono<String> getAuthorizationHeader();
    Mono<String> getAuthenticatedEmail();
}
