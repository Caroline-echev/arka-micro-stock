package com.arka.micro_stock.adapters.driving.reactive.controller;



import com.arka.micro_stock.adapters.driving.reactive.dto.request.CountryRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.CountryResponse;
import com.arka.micro_stock.adapters.driving.reactive.mapper.ICountryDtoMapper;
import com.arka.micro_stock.domain.api.ICountryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Country Controller", description = "Country Controller Operations")
public class CountryController {

    private final ICountryServicePort countryServicePort;
    private final ICountryDtoMapper countryDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new country", description = "Create a new country")
    public Mono<Void> createCountry(@Valid @RequestBody CountryRequest request) {
        return countryServicePort.createCountry(countryDtoMapper.toModel(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a country by id", description = "Get a country by id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CountryResponse> getCountryById(@PathVariable Long id) {
        return countryServicePort.getCountryById(id).map(countryDtoMapper::toResponse);
    }

    @GetMapping
    @Operation(summary = "List all countries alphabetically", description = "List all countries by name in ascending order")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CountryResponse> getAllCountries() {
        return countryServicePort.getAllCountries()
                .map(countryDtoMapper::toResponse);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a country by id", description = "Update a country by id")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> updateCountry(@PathVariable Long id, @Valid @RequestBody CountryRequest request) {
        return countryServicePort.updateCountry(id, countryDtoMapper.toModel(request));
    }


}