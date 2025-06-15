package com.arka.micro_stock.domain.spi;

import reactor.core.publisher.Mono;

public interface IUserPersistencePort {

    Mono<Boolean> existsByIdAndValidRole(Long supervisorId, String role);
}
