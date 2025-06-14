package com.arka.micro_stock.domain.api;

import com.arka.micro_stock.domain.model.InventoryModel;
import reactor.core.publisher.Mono;

public interface IInventoryServicePort {
    Mono<Void> createInventory(InventoryModel inventoryModel);
}
