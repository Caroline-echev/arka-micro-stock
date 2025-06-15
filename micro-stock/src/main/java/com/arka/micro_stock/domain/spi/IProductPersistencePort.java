package com.arka.micro_stock.domain.spi;

import reactor.core.publisher.Mono;

public interface IProductPersistencePort {
    Mono<Boolean> existsById(Long id);

}
