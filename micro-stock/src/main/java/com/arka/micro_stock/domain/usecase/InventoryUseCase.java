package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.IInventoryServicePort;
import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.*;
import com.arka.micro_stock.domain.util.validation.HistoricalInventoryValidator;
import com.arka.micro_stock.domain.util.validation.InventoryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.arka.micro_stock.domain.util.constants.InventoryConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryUseCase implements IInventoryServicePort {

    private final IInventoryPersistencePort inventoryPersistencePort;
    private final IInventorySupplierPersistencePort inventorySupplierPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final ICountryPersistencePort countryPersistencePort;
    private final ISupplierPersistencePort supplierPersistencePort;
    private final HistoricalInventoryValidator historicalInventoryValidator;

    @Override
    public Mono<Void> createInventory(InventoryModel inventoryModel) {
        log.info("Creating inventory for productId: {}, countryId: {}", inventoryModel.getProductId(), inventoryModel.getCountryId());

        return Mono.when(
                InventoryValidator.validateStock(inventoryModel),
                InventoryValidator.validateAtLeastOneSupplier(inventoryModel),
                InventoryValidator.validateProductExists(inventoryModel.getProductId(), productPersistencePort),
                InventoryValidator.validateCountryExists(inventoryModel.getCountryId(), countryPersistencePort),
                InventoryValidator.validateSuppliersExist(inventoryModel.getSuppliers(), supplierPersistencePort),
                InventoryValidator.validateNotExistsProductInCountry(inventoryModel.getProductId(), inventoryModel.getCountryId(), inventoryPersistencePort)
        ).then(Mono.defer(() -> {
            inventoryModel.setCreatedAt(LocalDateTime.now());
            inventoryModel.setUpdatedAt(LocalDateTime.now());

            return inventoryPersistencePort.save(inventoryModel)
                    .flatMap(savedInventory ->
                            Flux.fromIterable(inventoryModel.getSuppliers())
                                    .flatMap(supplier -> {
                                        supplier.setInventoryId(savedInventory.getId());
                                        return inventorySupplierPersistencePort.saveInventorySupplier(supplier);
                                    }) .flatMap(savedSuppliers ->
                                            historicalInventoryValidator.createTransaction(
                                                    savedInventory, savedSuppliers, "CREATE", "COMPLETE" )
                                    ).then()
                    );
        }));
    }

    @Override
    public Mono<InventoryModel> getInventoryByProductIdAndCountryId(Long productId, Long countryId) {
        log.info("Fetching inventory for productId: {}, countryId: {}", productId, countryId);

        return inventoryPersistencePort.getInventoryByProductIdAndCountryId(productId, countryId)
                .flatMap(inventory ->
                        inventorySupplierPersistencePort.getInventorySuppliersByInventoryId(inventory.getId())
                                .collectList()
                                .map(suppliers -> {
                                    inventory.setSuppliers(suppliers);
                                    return inventory;
                                })
                );
    }

    @Override
    public Flux<InventoryModel> getAllInventories() {
        log.info("Fetching all inventories");

        return inventoryPersistencePort.findAll()
                .flatMap(inventory ->
                        inventorySupplierPersistencePort.getInventorySuppliersByInventoryId(inventory.getId())
                                .collectList()
                                .map(suppliers -> {
                                    inventory.setSuppliers(suppliers);
                                    return inventory;
                                })
                );
    }

    @Override
    public Mono<Void> addSuppliersToInventory(Long inventoryId, List<InventorySupplierModel> supplierModels) {
        log.info("Adding suppliers to inventory: {}", inventoryId);

        return inventoryPersistencePort.getInventoryById(inventoryId)
                .flatMap(inventory ->
                        Flux.fromIterable(supplierModels)
                                .flatMap(supplier -> {
                                    supplier.setInventoryId(inventory.getId());
                                    return inventorySupplierPersistencePort.saveInventorySupplier(supplier);
                                }).then()
                );
    }

    @Override
    public Mono<Void> deleteSupplierFromInventory(Long inventoryId, Long supplierId) {
        log.info("Deleting supplier {} from inventory {}", supplierId, inventoryId);

        return inventorySupplierPersistencePort.findByInventoryIdAndSupplierId(inventoryId, supplierId)
                .switchIfEmpty(Mono.error(new NotFoundException(SUPPLIER_NOT_ASSOCIATED)))
                .flatMap(ignored -> inventorySupplierPersistencePort.deleteSupplierFromInventory(inventoryId, supplierId));
    }

    @Override
    public Mono<Void> addStockToInventory(Long inventoryId, Integer cantidad) {
        log.info("Adding stock to inventoryId: {} with amount: {}", inventoryId, cantidad);

        return inventoryPersistencePort.getInventoryById(inventoryId)
                .flatMap(inventory -> {
                    inventory.setStockActual(inventory.getStockActual() + cantidad);
                    inventory.setUpdatedAt(LocalDateTime.now());
                    return inventoryPersistencePort.save(inventory).flatMap(savedInventory ->
                            historicalInventoryValidator.createTransaction(
                                    savedInventory, savedInventory.getSuppliers().get(0), "CREATE", "COMPLETE" )
                    ).then();
                });
    }
}
nsaction