package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IInventorySupplierEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IInventorySupplierRepository;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.IInventorySupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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
}
