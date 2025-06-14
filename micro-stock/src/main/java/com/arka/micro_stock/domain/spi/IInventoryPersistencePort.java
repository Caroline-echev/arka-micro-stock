package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.InventoryModel;
import reactor.core.publisher.Mono;

public interface IInventoryPersistencePort {
    Mono<Boolean> existsByProductIdAndCountryId(Long productId, Long countryId);
    Mono<InventoryModel> save(InventoryModel inventoryModel);
}
