package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.ProviderModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProviderPersistencePort {
    Mono<Void> save(ProviderModel providerModel);
    Mono<Boolean> existsByName(String name);
    Mono<Boolean> existsByEmail(String email);
    Flux<ProviderModel> findAll();
    Mono<ProviderModel> findByName(String name);
    Mono<ProviderModel> findById(Long id);
}
