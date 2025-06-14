package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.InventorySupplierModel;
import reactor.core.publisher.Mono;

public interface IInventorySupplierPersistencePort {
   Mono<InventorySupplierModel> saveInventorySupplier(InventorySupplierModel inventorySupplierModel);
}
