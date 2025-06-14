package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IInventorySupplierEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IInventorySupplierRepository;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.IInventorySupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventorySupplierAdapter implements IInventorySupplierPersistencePort {

    private final IInventorySupplierRepository inventorySupplierRepository;
    private final IInventorySupplierEntityMapper inventorySupplierMapper;



    @Override
    public Mono<InventorySupplierModel> saveInventorySupplier(InventorySupplierModel inventorySupplierModel) {
        return inventorySupplierRepository.save(inventorySupplierMapper.toEntity(inventorySupplierModel))
                .map(inventorySupplierMapper::toModel);
    }
    @Override
    public Flux<InventorySupplierModel> getInventorySuppliersByInventoryId(Long inventoryId) {
        return inventorySupplierRepository.findByInventoryId(inventoryId)
                .switchIfEmpty(Mono.error(new NotFoundException("No se encontraron proveedores para el inventario con ID " + inventoryId)))
                .map(inventorySupplierMapper::toModel);
    }

    @Override
    public Mono<Void> deleteSupplierFromInventory(Long inventoryId, Long supplierId) {
        return inventorySupplierRepository.deleteByInventoryIdAndSupplierId(inventoryId, supplierId)
                .then();
    }
}
