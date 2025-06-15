package com.arka.micro_stock.adapters.driving.reactive.controller;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.SupplierRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.SupplierResponse;
import com.arka.micro_stock.adapters.driving.reactive.mapper.ISupplierDtoMapper;
import com.arka.micro_stock.domain.api.ISupplierServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Supplier Controller", description = "Supplier Controller Operations")
public class SupplierController {

    private final ISupplierServicePort supplierServicePort;
    private final ISupplierDtoMapper supplierDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new supplier", description = "Create a new supplier")
    public Mono<Void> createSupplier(@Valid @RequestBody SupplierRequest request) {
        return supplierServicePort.createSupplier(supplierDtoMapper.toModel(request));
    }
    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Listar todos los proveedores ordenados alfabéticamente y con filtros opcionales por estado y país")
    @ResponseStatus(HttpStatus.OK)
    public Flux<SupplierResponse> getSuppliers(
            @RequestParam(required = false) String country
    ) {
        return supplierServicePort.getSuppliers( country)
                .map(supplierDtoMapper::toResponse);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get a supplier by name", description = "Get a supplier by name")
    @ResponseStatus(HttpStatus.OK)
    public Mono<SupplierResponse> getSupplierByName(@PathVariable String name) {
        return supplierServicePort.getSupplierByName(name).map(supplierDtoMapper::toResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier by id", description = "Update a supplier by id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierRequest request) {
        return supplierServicePort.updateSupplier(id, supplierDtoMapper.toModel(request));
    }
}
