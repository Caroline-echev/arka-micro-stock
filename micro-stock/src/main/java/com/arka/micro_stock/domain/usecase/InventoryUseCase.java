package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.IInventoryServicePort;
import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.model.CountryModel;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.*;
import com.arka.micro_stock.domain.util.validation.InventoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryUseCase implements IInventoryServicePort {

    private final IInventoryPersistencePort inventoryPersistencePort;
    private final IInventorySupplierPersistencePort inventorySupplierPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final ICountryPersistencePort countryPersistencePort;
    private final ISupplierPersistencePort supplierPersistencePort;

    @Override
    public Mono<Void> createInventory(InventoryModel inventoryModel) {
        return Mono.when(
                InventoryValidator.validateStock(inventoryModel),
                InventoryValidator.validateAtLeastOneSupplier(inventoryModel),
                InventoryValidator.validateProductExists(inventoryModel.getProductId(), productPersistencePort),
                InventoryValidator.validateCountryExists(inventoryModel.getCountryId(), countryPersistencePort),
                InventoryValidator.validateSuppliersExist(inventoryModel.getSuppliers(), supplierPersistencePort),
                InventoryValidator.validateNotExistsProductInCountry(inventoryModel.getProductId(), inventoryModel.getCountryId(), inventoryPersistencePort)
        ).then(
                Mono.defer(() -> {
                    inventoryModel.setCreatedAt(LocalDateTime.now());
                    inventoryModel.setUpdatedAt(LocalDateTime.now());
                    return inventoryPersistencePort.save(inventoryModel)
                            .flatMap(savedInventory -> {
                                return Flux.fromIterable(inventoryModel.getSuppliers())
                                        .flatMap(inventorySupplierModel -> {
                                            inventorySupplierModel.setInventoryId(savedInventory.getId());
                                            return inventorySupplierPersistencePort.saveInventorySupplier(inventorySupplierModel);
                                        })
                                        .then();
                            });
                })
        );
    }

    @Override
    public Mono<InventoryModel> getInventoryByProductIdAndCountryId(Long productId, Long countryId) {
        return inventoryPersistencePort.getInventoryByProductIdAndCountryId(productId, countryId)
                .flatMap(inventoryModel ->
                        inventorySupplierPersistencePort.getInventorySuppliersByInventoryId(inventoryModel.getId())
                                .collectList()
                                .map(suppliers -> {
                                    inventoryModel.setSuppliers(suppliers);
                                    return inventoryModel;
                                })
                );
    }

    @Override
    public Flux<InventoryModel> getAllInventories() {
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
    public Mono<Void> addSuppliersToInventory(Long inventoryId, List<InventorySupplierModel> inventorySupplierModels) {
        return inventoryPersistencePort.getInventoryById(inventoryId)
                .flatMap(savedInventory -> {
                    return Flux.fromIterable(inventorySupplierModels)
                            .flatMap(inventorySupplierModel -> {
                                inventorySupplierModel.setInventoryId(savedInventory.getId());
                                return inventorySupplierPersistencePort.saveInventorySupplier(inventorySupplierModel);
                            })
                            .then();
                });
    }
    @Override
    public Mono<Void> deleteSupplierFromInventory(Long inventoryId, Long supplierId) {
        return inventorySupplierPersistencePort.findByInventoryIdAndSupplierId(inventoryId, supplierId)
                .switchIfEmpty(Mono.error(new IllegalStateException("El proveedor no está asociado al inventario")))
                .flatMap(ignored -> inventorySupplierPersistencePort.deleteSupplierFromInventory(inventoryId, supplierId));
    }

    @Override
    public Mono<Void> updateSupplierPrice(Long inventoryId, Long supplierId, BigDecimal price) {
        if (price == null) {
            return Mono.error(new BadRequestException("El precio debe ser mayor a 0"));
        }

        return inventorySupplierPersistencePort.findByInventoryIdAndSupplierId(inventoryId, supplierId)
                .switchIfEmpty(Mono.error(new BadRequestException("El proveedor no está asociado al inventario")))
                .flatMap(existing -> {
                    if (existing.getPrice().equals(price)) {
                        return Mono.error(new BadRequestException("El nuevo precio es igual al actual"));
                    }
                    existing.setPrice(price);
                    return inventorySupplierPersistencePort.save(existing).then();
                });
    }

    @Override
    public Mono<Void> addStockToInventory(Long inventoryId, Integer cantidad) {
        return inventoryPersistencePort.getInventoryById(inventoryId)
                .flatMap(savedInventory -> {
                    savedInventory.setStockActual(savedInventory.getStockActual() + cantidad);
                    return inventoryPersistencePort.save(savedInventory).then();
                });
    }


}
