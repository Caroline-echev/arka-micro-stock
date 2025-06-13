package com.arka.micro_stock.adapters.driving.reactive.controller;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.ProviderRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.ProviderResponse;
import com.arka.micro_stock.adapters.driving.reactive.mapper.IProviderDtoMapper;
import com.arka.micro_stock.domain.api.IProviderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
@Tag(name = "Provider Controller", description = "Provider Controller Operations")
public class ProviderController {

    private final IProviderServicePort providerServicePort;
    private final IProviderDtoMapper providerDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new provider", description = "Create a new provider")
    public Mono<Void> createProvider(@Valid @RequestBody ProviderRequest request) {
        return providerServicePort.createProvider(providerDtoMapper.toModel(request));
    }
    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Listar todos los proveedores ordenados alfabéticamente y con filtros opcionales por estado y país")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProviderResponse> getProviders(
            @RequestParam(required = false) String country
    ) {
        return providerServicePort.getProviders( country)
                .map(providerDtoMapper::toResponse);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Get a provider by name", description = "Get a provider by name")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProviderResponse> getProviderByName(@PathVariable String name) {
        return providerServicePort.getProviderByName(name).map(providerDtoMapper::toResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a provider by id", description = "Update a provider by id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateProvider(@PathVariable Long id, @Valid @RequestBody ProviderRequest request) {
        return providerServicePort.updateProvider(id, providerDtoMapper.toModel(request));
    }
}
