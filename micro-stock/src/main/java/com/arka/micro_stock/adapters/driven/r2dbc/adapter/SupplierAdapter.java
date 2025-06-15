package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.ISupplierEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.ISupplierRepository;
import com.arka.micro_stock.domain.model.SupplierModel;
import com.arka.micro_stock.domain.spi.ISupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class SupplierAdapter implements ISupplierPersistencePort {

    private final ISupplierRepository supplierRepository;
    private final ISupplierEntityMapper supplierEntityMapper;

    @Override
    public Mono<Void> save(SupplierModel supplier) {
        log.debug("Saving supplier with name={} and email={}", supplier.getName(), supplier.getEmail());
        return supplierRepository
                .save(supplierEntityMapper.toEntity(supplier))
                .doOnSuccess(saved -> log.info("Supplier saved: name={}, email={}", supplier.getName(), supplier.getEmail()))
                .then();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        log.debug("Checking existence of supplier by name: {}", name);
        return supplierRepository.findByName(name)
                .hasElement()
                .doOnNext(exists -> log.info("Supplier exists by name '{}': {}", name, exists));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        log.debug("Checking existence of supplier by email: {}", email);
        return supplierRepository.findByEmail(email)
                .hasElement()
                .doOnNext(exists -> log.info("Supplier exists by email '{}': {}", email, exists));
    }

    @Override
    public Flux<SupplierModel> findAll() {
        log.debug("Fetching all suppliers");
        return supplierRepository.findAll()
                .doOnComplete(() -> log.info("All suppliers fetched"))
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<SupplierModel> findByName(String name) {
        log.debug("Finding supplier by name: {}", name);
        return supplierRepository.findByName(name)
                .doOnNext(supplier -> log.info("Supplier found by name {}: id={}", name, supplier.getId()))
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<SupplierModel> findById(Long id) {
        log.debug("Finding supplier by id: {}", id);
        return supplierRepository.findById(id)
                .doOnNext(supplier -> log.info("Supplier found by id {}: name={}", id, supplier.getName()))
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        log.debug("Checking if supplier exists by id: {}", id);
        return supplierRepository.existsById(id)
                .doOnNext(exists -> log.info("Supplier exists by id {}: {}", id, exists));
    }
}
