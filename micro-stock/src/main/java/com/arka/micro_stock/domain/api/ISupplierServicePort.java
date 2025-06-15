package com.arka.micro_stock.domain.api;

import com.arka.micro_stock.domain.model.SupplierModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ISupplierServicePort {
    Mono<Void> createSupplier(SupplierModel supplierModel);
    Flux<SupplierModel> getSuppliers( String country);
    Mono<SupplierModel> getSupplierByName(String name);
    Mono<Void> updateSupplier(Long id,SupplierModel supplierModel);
}
