package com.arka.micro_stock.adapters.driving.reactive.controller;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventoryRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventorySupplierRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.InventoryResponse;
import com.arka.micro_stock.adapters.driving.reactive.mapper.IInventoryDtoMapper;
import com.arka.micro_stock.domain.api.IInventoryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Controller", description = "Inventory API Controller Operations")
public class InventoryController {

    private final IInventoryServicePort inventoryServicePort;
    private final IInventoryDtoMapper inventoryDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new inventory", description = "Create a new inventory")
    public Mono<Void> createInventory(@Valid @RequestBody InventoryRequest request) {
        return inventoryServicePort.createInventory(inventoryDtoMapper.toModel(request));
    }

    @GetMapping("/{productId}/{countryId}")
    @Operation(summary = "Get a inventory by productId and countryId", description = "Get a inventory by productId and countryId")
    @ResponseStatus(HttpStatus.OK)
    public Mono<InventoryResponse> getInventoryByProductIdAndCountryId(@PathVariable Long productId,@PathVariable Long countryId) {
        return inventoryServicePort.getInventoryByProductIdAndCountryId(productId, countryId).map(inventoryDtoMapper::toResponse);
    }

    @GetMapping
    @Operation(summary = "Get all inventories", description = "Get all inventories")
    @ResponseStatus(HttpStatus.OK)
    public Flux<InventoryResponse> getAllInventories() {
        return inventoryServicePort.getAllInventories().map(inventoryDtoMapper::toResponse);
    }

    @PatchMapping("/{inventoryId}/suppliers")
    @Operation(summary = "Agregar proveedores a un inventario")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> addSuppliersToInventory(
            @PathVariable Long inventoryId,
            @RequestBody List<InventorySupplierRequest> supplierRequests) {
        return inventoryServicePort.addSuppliersToInventory(inventoryId, inventoryDtoMapper.toSupplierModelList(supplierRequests));
    }


}




