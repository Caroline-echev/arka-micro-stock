package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IInventoryEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IInventoryRepository;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.spi.IInventoryPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryAdapter implements IInventoryPersistencePort {

    private final IInventoryRepository inventoryRepository;
    private final IInventoryEntityMapper inventoryMapper;

    @Override
    public Mono<InventoryModel> save(InventoryModel inventoryModel) {
        log.debug("Saving inventory: productId={}, countryId={}",
                inventoryModel.getProductId(), inventoryModel.getCountryId());
        return inventoryRepository.save(inventoryMapper.toEntity(inventoryModel))
                .doOnSuccess(saved -> log.info("Inventory saved for productId={}, countryId={}",
                        inventoryModel.getProductId(), inventoryModel.getCountryId()))
                .map(inventoryMapper::toModel);
    }

    @Override
    public Mono<InventoryModel> getInventoryByProductIdAndCountryId(Long productId, Long countryId) {
        log.debug("Fetching inventory by productId={} and countryId={}", productId, countryId);
        return inventoryRepository.findByProductIdAndCountryId(productId, countryId)
                .doOnNext(inv -> log.info("Inventory found for productId={}, countryId={}", productId, countryId))
                .map(inventoryMapper::toModel);
    }

    @Override
    public Flux<InventoryModel> findAll() {
        log.debug("Fetching all inventories");
        return inventoryRepository.findAll()
                .doOnComplete(() -> log.info("Completed fetching all inventories"))
                .map(inventoryMapper::toModel);
    }

    @Override
    public Mono<InventoryModel> getInventoryById(Long id) {
        log.debug("Fetching inventory by ID: {}", id);
        return inventoryRepository.findById(id)
                .doOnNext(inv -> log.info("Inventory found with ID: {}", id))
                .map(inventoryMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsByProductIdAndCountryId(Long productId, Long countryId) {
        log.debug("Checking if inventory exists for productId={}, countryId={}", productId, countryId);
        return inventoryRepository.existsByProductIdAndCountryId(productId, countryId)
                .doOnNext(exists -> log.info("Inventory exists for productId={}, countryId={}: {}",
                        productId, countryId, exists));
    }
}
