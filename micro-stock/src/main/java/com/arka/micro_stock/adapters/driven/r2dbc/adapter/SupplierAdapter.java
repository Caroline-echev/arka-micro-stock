package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.ISupplierEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.ISupplierRepository;
import com.arka.micro_stock.domain.model.SupplierModel;
import com.arka.micro_stock.domain.spi.ISupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierAdapter implements ISupplierPersistencePort {

    private final ISupplierRepository supplierRepository;
    private final ISupplierEntityMapper supplierEntityMapper;

    @Override
    public Mono<Void> save(SupplierModel supplier) {
        return supplierRepository
                .save(supplierEntityMapper.toEntity(supplier))
                .then();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return supplierRepository.findByName(name).hasElement();
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return supplierRepository.findByEmail(email).hasElement();
    }

    @Override
    public Flux<SupplierModel> findAll() {
        return supplierRepository.findAll()
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<SupplierModel> findByName(String name) {
        return supplierRepository.findByName(name)
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<SupplierModel> findById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        return supplierRepository.existsById(id);
    }


}
