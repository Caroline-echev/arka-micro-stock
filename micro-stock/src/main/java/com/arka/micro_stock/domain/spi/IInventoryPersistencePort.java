package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.InventoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IInventoryPersistencePort {
    Mono<Boolean> existsByProductIdAndCountryId(Long productId, Long countryId);
    Mono<InventoryModel> save(InventoryModel inventoryModel);
    Mono<InventoryModel> getInventoryByProductIdAndCountryId(Long productId, Long countryId);
    Flux<InventoryModel> findAll();
    Mono<InventoryModel> getInventoryById(Long id);
}
