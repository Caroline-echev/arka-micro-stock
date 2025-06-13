package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.CountryModel;
import reactor.core.publisher.Mono;

public interface ICountryPersistencePort {
    Mono<Void> saveCountry(CountryModel country);
    Mono<Boolean> existsByName(String name);
    Mono<Boolean> existsByLogisticsSupervisorId(Long supervisorId);
    Mono<CountryModel> findById(Long id);
   }
