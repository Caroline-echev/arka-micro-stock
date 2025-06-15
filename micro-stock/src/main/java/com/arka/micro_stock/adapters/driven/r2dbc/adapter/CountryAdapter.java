package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.ICountryEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.ICountryRepository;
import com.arka.micro_stock.domain.model.CountryModel;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CountryAdapter implements ICountryPersistencePort {

    private final ICountryRepository countryRepository;
    private final ICountryEntityMapper countryEntityMapper;

    @Override
    public Mono<Void> saveCountry(CountryModel country) {
        log.debug("Saving country: {}", country.getName());
        return countryRepository.save(countryEntityMapper.toEntity(country))
                .doOnSuccess(saved -> log.info("Country '{}' saved successfully", country.getName()))
                .map(countryEntityMapper::toModel)
                .then();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        log.debug("Checking if country exists by name: {}", name);
        return countryRepository.findByName(name)
                .hasElement()
                .doOnNext(exists -> log.info("Country with name '{}' exists: {}", name, exists));
    }

    @Override
    public Mono<Boolean> existsByLogisticsSupervisorId(Long supervisorId) {
        log.debug("Checking if any country is assigned to logistics supervisor ID: {}", supervisorId);
        return countryRepository.existsByLogisticsSupervisorId(supervisorId)
                .doOnNext(exists -> log.info("Country with supervisor ID '{}' exists: {}", supervisorId, exists));
    }

    @Override
    public Mono<CountryModel> findById(Long id) {
        log.debug("Finding country by ID: {}", id);
        return countryRepository.findById(id)
                .doOnNext(country -> log.info("Found country with ID: {}", id))
                .map(countryEntityMapper::toModel);
    }

    @Override
    public Flux<CountryModel> findAllOrderByNameAsc() {
        log.debug("Fetching all countries ordered by name ascending");
        return countryRepository.findAllByOrderByNameAsc()
                .doOnComplete(() -> log.info("Fetched all countries ordered by name"))
                .map(countryEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> existsById(Long id) {
        log.debug("Checking if country exists by ID: {}", id);
        return countryRepository.existsById(id)
                .doOnNext(exists -> log.info("Country with ID '{}' exists: {}", id, exists));
    }
}
