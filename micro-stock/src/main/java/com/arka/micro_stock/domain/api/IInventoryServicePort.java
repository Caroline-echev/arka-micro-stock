package com.arka.micro_stock.domain.api;

import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import org.w3c.dom.stylesheets.LinkStyle;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

public interface IInventoryServicePort {
    Mono<Void> createInventory(InventoryModel inventoryModel);

    Mono<InventoryModel> getInventoryByProductIdAndCountryId(Long productId, Long countryId);

    Flux<InventoryModel> getAllInventories();

    Mono<Void> addSuppliersToInventory(Long inventoryId, List<InventorySupplierModel> inventorySupplierModels);

    Mono<Void> deleteSupplierFromInventory(Long inventoryId, Long supplierId);


    Mono<Void> addStockToInventory(Long inventoryId, Integer cantidad);

}