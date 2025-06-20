package com.arka.micro_stock.domain.api;

import com.arka.micro_stock.domain.model.CountryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ICountryServicePort {

    Mono<Void> createCountry(CountryModel countryModel);
    Mono<CountryModel> getCountryById(Long id);
    Flux<CountryModel> getAllCountries();
    Mono<Void> updateCountry(Long id, CountryModel countryModel);
}