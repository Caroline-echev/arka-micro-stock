package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.ISupplierServicePort;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.SupplierModel;
import com.arka.micro_stock.domain.spi.ISupplierPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

import static com.arka.micro_stock.domain.util.constants.SupplierConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class SupplierUseCase implements ISupplierServicePort {

    private final ISupplierPersistencePort supplierPersistencePort;

    @Override
    public Mono<Void> createSupplier(SupplierModel supplier) {
        log.info("Creating supplier: {}", supplier.getName());
        return validateUniqueNameAndEmail(supplier)
                .then(supplierPersistencePort.save(supplier))
                .doOnSuccess(v -> log.info("Supplier created successfully: {}", supplier.getName()))
                .doOnError(error -> log.error("Error creating supplier: {}", error.getMessage()));
    }

    @Override
    public Flux<SupplierModel> getSuppliers(String country) {
        log.info("Fetching suppliers{}", country != null ? " filtered by country: " + country : "");
        return supplierPersistencePort.findAll()
                .doOnSubscribe(subscription -> log.debug("Querying all suppliers"))
                .filter(supplier -> country == null ||
                        (supplier.getAddress() != null &&
                                supplier.getAddress().toLowerCase().contains(country.toLowerCase())))
                .sort(Comparator.comparing(SupplierModel::getName))
                .doOnComplete(() -> log.info("Completed fetching suppliers"));
    }

    @Override
    public Mono<SupplierModel> getSupplierByName(String name) {
        log.info("Fetching supplier by name: {}", name);
        return supplierPersistencePort.findByName(name)
                .doOnSuccess(supplier -> {
                    if (supplier != null) {
                        log.info("Supplier found: {}", supplier.getName());
                    } else {
                        log.warn("No supplier found with name: {}", name);
                    }
                });
    }

    @Override
    public Mono<Void> updateSupplier(Long id, SupplierModel supplierModel) {
        log.info("Updating supplier with id: {}", id);
        return supplierPersistencePort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Supplier not found with id: {}", id);
                    return Mono.error(new NotFoundException(SUPPLIER_NOT_FOUND));
                }))
                .flatMap(existing -> {
                    supplierModel.setId(id);
                    log.debug("Saving updated supplier: {}", supplierModel);
                    return supplierPersistencePort.save(supplierModel);
                })
                .doOnSuccess(v -> log.info("Supplier updated successfully with id: {}", id))
                .doOnError(error -> log.error("Error updating supplier with id {}: {}", id, error.getMessage()));
    }

    private Mono<Void> validateUniqueNameAndEmail(SupplierModel supplier) {
        log.debug("Validating uniqueness for supplier name: {}, email: {}", supplier.getName(), supplier.getEmail());
        return supplierPersistencePort.existsByName(supplier.getName())
                .flatMap(existsByName -> {
                    if (existsByName) {
                        log.warn("Supplier name already exists: {}", supplier.getName());
                        return Mono.error(new DuplicateResourceException(SUPPLIER_NAME_ALREADY_EXISTS));
                    }
                    return Mono.empty();
                })
                .then(supplierPersistencePort.existsByEmail(supplier.getEmail()))
                .flatMap(existsByEmail -> {
                    if (existsByEmail) {
                        log.warn("Supplier email already exists: {}", supplier.getEmail());
                        return Mono.error(new DuplicateResourceException(SUPPLIER_EMAIL_ALREADY_EXISTS));
                    }
                    return Mono.empty();
                });
    }
}
