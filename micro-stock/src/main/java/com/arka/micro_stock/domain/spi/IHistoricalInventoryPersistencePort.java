package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.InventoryHistoryModel;
import reactor.core.publisher.Mono;

public interface IHistoricalInventoryPersistencePort {

    Mono<InventoryHistoryModel> save(InventoryHistoryModel inventoryHistoryModel);
}
