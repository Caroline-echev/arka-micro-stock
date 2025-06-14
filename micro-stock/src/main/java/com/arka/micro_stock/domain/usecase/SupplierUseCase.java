package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.ISupplierServicePort;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.SupplierModel;
import com.arka.micro_stock.domain.spi.ISupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class SupplierUseCase implements ISupplierServicePort {

    private final ISupplierPersistencePort supplierPersistencePort;

    @Override
    public Mono<Void> createSupplier(SupplierModel supplier) {
        return validateUniqueNameAndEmail(supplier)
                .then(supplierPersistencePort.save(supplier));
    }


    @Override
    public Flux<SupplierModel> getSuppliers( String country) {
        return supplierPersistencePort.findAll()
                .filter(supplier -> country == null ||
                        (supplier.getAddress() != null && supplier.getAddress().toLowerCase().contains(country.toLowerCase())))
                .sort(Comparator.comparing(SupplierModel::getName));
    }

    @Override
    public Mono<SupplierModel> getSupplierByName(String name) {
        return supplierPersistencePort.findByName(name);
    }

    @Override
    public Mono<Void> updateSupplier(Long id, SupplierModel supplierModel) {
        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Supplier not found")))
                .flatMap(existing -> {
                    supplierModel.setId(id);
                    return supplierPersistencePort.save(supplierModel);
                });
    }


    private Mono<Void> validateUniqueNameAndEmail(SupplierModel supplier) {
        return supplierPersistencePort.existsByName(supplier.getName())
                .flatMap(existsByName -> {
                    if (existsByName) {
                        return Mono.error(new DuplicateResourceException("Supplier name already exists"));
                    }
                    return Mono.empty();
                })
                .then(supplierPersistencePort.existsByEmail(supplier.getEmail()))
                .flatMap(existsByEmail -> {
                    if (existsByEmail) {
                        return Mono.error(new DuplicateResourceException("Supplier email already exists"));
                    }
                    return Mono.empty();
                });
    }
}
