package com.arka.micro_stock.adapters.driving.reactive.controller;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventoryRequest;
import com.arka.micro_stock.adapters.driving.reactive.mapper.IInventoryDtoMapper;
import com.arka.micro_stock.domain.api.IInventoryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
}
