package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.SupplierEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ISupplierRepository extends ReactiveCrudRepository<SupplierEntity, Long> {
    Mono<SupplierEntity> findByName(String name);
    Mono<SupplierEntity> findByEmail(String email);

}
