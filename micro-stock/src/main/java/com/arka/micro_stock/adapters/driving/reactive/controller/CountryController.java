package com.arka.micro_stock.adapters.driving.reactive.controller;



import com.arka.micro_stock.adapters.driving.reactive.dto.request.CountryRequest;
import com.arka.micro_stock.adapters.driving.reactive.mapper.ICountryDtoMapper;
import com.arka.micro_stock.domain.api.ICountryServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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


}