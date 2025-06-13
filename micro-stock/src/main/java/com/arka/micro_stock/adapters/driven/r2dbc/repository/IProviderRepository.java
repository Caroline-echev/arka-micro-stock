package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.ProviderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface IProviderRepository extends ReactiveCrudRepository<ProviderEntity, Long> {
    Mono<ProviderEntity> findByName(String name);
    Mono<ProviderEntity> findByEmail(String email);
}
