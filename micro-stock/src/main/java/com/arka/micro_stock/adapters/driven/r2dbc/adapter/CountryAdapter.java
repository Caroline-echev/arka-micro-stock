package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.ICountryEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.ICountryRepository;
import com.arka.micro_stock.domain.model.CountryModel;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CountryAdapter implements ICountryPersistencePort {

    private final ICountryRepository countryRepository;
    private final ICountryEntityMapper countryEntityMapper;


    @Override
    public Mono<Void> saveCountry(CountryModel country) {
        return countryRepository.save(countryEntityMapper.toEntity(country))
                .map(countryEntityMapper::toModel).then();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return countryRepository.findByName(name).hasElement();
    }

    @Override
    public Mono<Boolean> existsByLogisticsSupervisorId(Long supervisorId) {
        return countryRepository.existsByLogisticsSupervisorId(supervisorId);
    }

    @Override
    public Mono<CountryModel> findById(Long id) {
        return countryRepository.findById(id)
                .map(countryEntityMapper::toModel);
    }

    @Override
    public Flux<CountryModel> findAllOrderByNameAsc() {
        return countryRepository.findAllByOrderByNameAsc()
                .map(countryEntityMapper::toModel);
    }

}