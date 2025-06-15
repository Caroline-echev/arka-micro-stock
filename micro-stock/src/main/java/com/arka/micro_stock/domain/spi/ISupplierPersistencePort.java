package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.SupplierModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISupplierPersistencePort {
    Mono<Void> save(SupplierModel supplierModel);
    Mono<Boolean> existsByName(String name);
    Mono<Boolean> existsByEmail(String email);
    Flux<SupplierModel> findAll();
    Mono<SupplierModel> findByName(String name);
    Mono<SupplierModel> findById(Long id);
    Mono<Boolean> existsById(Long id);
}
