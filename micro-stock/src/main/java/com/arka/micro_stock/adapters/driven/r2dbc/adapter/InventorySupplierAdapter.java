package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IInventorySupplierEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IInventorySupplierRepository;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.IInventorySupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.arka.micro_stock.adapters.driven.r2dbc.util.ConstantsR2DBC.NO_SUPPLIERS_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventorySupplierAdapter implements IInventorySupplierPersistencePort {

    private final IInventorySupplierRepository inventorySupplierRepository;
    private final IInventorySupplierEntityMapper inventorySupplierMapper;

    @Override
    public Mono<InventorySupplierModel> saveInventorySupplier(InventorySupplierModel inventorySupplierModel) {
        log.debug("Saving InventorySupplier: inventoryId={}, supplierId={}",
                inventorySupplierModel.getInventoryId(), inventorySupplierModel.getSupplierId());
        return inventorySupplierRepository.save(inventorySupplierMapper.toEntity(inventorySupplierModel))
                .doOnSuccess(saved -> log.info("InventorySupplier saved: inventoryId={}, supplierId={}",
                        inventorySupplierModel.getInventoryId(), inventorySupplierModel.getSupplierId()))
                .map(inventorySupplierMapper::toModel);
    }

    @Override
    public Flux<InventorySupplierModel> getInventorySuppliersByInventoryId(Long inventoryId) {
        log.debug("Fetching suppliers for inventoryId={}", inventoryId);
        return inventorySupplierRepository.findByInventoryId(inventoryId)
                .switchIfEmpty(Mono.error(new NotFoundException(NO_SUPPLIERS_FOUND + inventoryId)))
                .doOnComplete(() -> log.info("Fetched suppliers for inventoryId={}", inventoryId))
                .map(inventorySupplierMapper::toModel);
    }

    @Override
    public Mono<Void> deleteSupplierFromInventory(Long inventoryId, Long supplierId) {
        log.debug("Deleting supplierId={} from inventoryId={}", supplierId, inventoryId);
        return inventorySupplierRepository.deleteByInventoryIdAndSupplierId(inventoryId, supplierId)
                .doOnSuccess(unused -> log.info("Deleted supplierId={} from inventoryId={}", supplierId, inventoryId))
                .then();
    }

    @Override
    public Mono<InventorySupplierModel> findByInventoryIdAndSupplierId(Long inventoryId, Long supplierId) {
        log.debug("Finding InventorySupplier by inventoryId={} and supplierId={}", inventoryId, supplierId);
        return inventorySupplierRepository.findByInventoryIdAndSupplierId(inventoryId, supplierId)
                .doOnNext(found -> log.info("Found InventorySupplier: inventoryId={}, supplierId={}", inventoryId, supplierId))
                .map(inventorySupplierMapper::toModel);
    }


}
