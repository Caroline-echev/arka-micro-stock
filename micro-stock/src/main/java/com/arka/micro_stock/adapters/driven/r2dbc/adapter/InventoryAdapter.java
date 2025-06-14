package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IInventoryEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IInventoryRepository;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.spi.IInventoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryAdapter implements IInventoryPersistencePort {

    private final IInventoryRepository inventoryRepository;
    private final IInventoryEntityMapper inventoryMapper;

    @Override
    public Mono<InventoryModel> save(InventoryModel inventoryModel) {
        return inventoryRepository.save(inventoryMapper.toEntity(inventoryModel))
                .map(inventoryMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByProductIdAndCountryId(Long productId, Long countryId) {
        return inventoryRepository.existsByProductIdAndCountryId(productId, countryId);
    }
}
