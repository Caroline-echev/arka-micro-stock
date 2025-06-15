package com.arka.micro_stock.domain.util.validation;

import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import com.arka.micro_stock.domain.spi.IInventoryPersistencePort;
import com.arka.micro_stock.domain.spi.IProductPersistencePort;
import com.arka.micro_stock.domain.spi.ISupplierPersistencePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static com.arka.micro_stock.domain.util.constants.InventoryConstants.*;

public class InventoryValidator {

    private static final Logger log = LoggerFactory.getLogger(InventoryValidator.class);

    public static Mono<Void> validateStock(InventoryModel inventoryModel) {
        log.info("Validating stock: actual={}, minimum={}",
                inventoryModel.getStockActual(), inventoryModel.getStockMinimo());

        if (inventoryModel.getStockActual() < inventoryModel.getStockMinimo()) {
            log.warn("Validation failed: Stock actual < Stock mínimo");
            return Mono.error(new BadRequestException(STOCK_VALIDATION_ERROR));
        }
        return Mono.empty();
    }

    public static Mono<Void> validateAtLeastOneSupplier(InventoryModel inventoryModel) {
        int count = inventoryModel.getSuppliers() != null ? inventoryModel.getSuppliers().size() : 0;
        log.info("Validating suppliers: count={}", count);

        if (count == 0) {
            log.warn("Validation failed: No suppliers provided");
            return Mono.error(new BadRequestException(SUPPLIER_REQUIRED_ERROR));
        }
        return Mono.empty();
    }

    public static Mono<Void> validateProductExists(Long productId, IProductPersistencePort productPersistencePort) {
        log.info("Validating product existence for productId={}", productId);
        return productPersistencePort.existsById(productId)
                .flatMap(exists -> {
                    if (!exists) {
                        log.warn("Validation failed: Product does not exist (id={})", productId);
                        return Mono.error(new BadRequestException(PRODUCT_NOT_FOUND_ERROR));
                    }
                    return Mono.empty();
                });
    }

    public static Mono<Void> validateCountryExists(Long countryId, ICountryPersistencePort countryPersistencePort) {
        log.info("Validating country existence for countryId={}", countryId);
        return countryPersistencePort.existsById(countryId)
                .flatMap(exists -> {
                    if (!exists) {
                        log.warn("Validation failed: Country does not exist (id={})", countryId);
                        return Mono.error(new BadRequestException(COUNTRY_NOT_FOUND_ERROR));
                    }
                    return Mono.empty();
                });
    }

    public static Mono<Void> validateSuppliersExist(Iterable<InventorySupplierModel> suppliers, ISupplierPersistencePort supplierPersistencePort) {
        int size = ((Collection<?>) suppliers).size();
        log.info("Validating existence of {} suppliers", size);
        return Flux.fromIterable(suppliers)
                .flatMap(s -> {
                    Long id = s.getSupplierId();
                    log.info("Checking supplier with id={}", id);
                    return supplierPersistencePort.existsById(id);
                })
                .all(Boolean::booleanValue)
                .flatMap(allValid -> {
                    if (!allValid) {
                        log.warn("Validation failed: One or more suppliers do not exist");
                        return Mono.error(new BadRequestException(SUPPLIERS_NOT_FOUND_ERROR));
                    }
                    return Mono.empty();
                });
    }

    public static Mono<Void> validateNotExistsProductInCountry(Long productId, Long countryId, IInventoryPersistencePort inventoryPersistencePort) {
        log.info("Validating uniqueness of productId={} in countryId={}", productId, countryId);
        return inventoryPersistencePort.existsByProductIdAndCountryId(productId, countryId)
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Validation failed: Product already exists in country");
                        return Mono.error(new BadRequestException(PRODUCT_ALREADY_EXISTS_ERROR));
                    }
                    return Mono.empty();
                });
    }
}
