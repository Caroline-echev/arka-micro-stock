package com.arka.micro_stock.adapters.driven.r2dbc.adapter;

import com.arka.micro_stock.adapters.driven.r2dbc.mapper.IProviderEntityMapper;
import com.arka.micro_stock.adapters.driven.r2dbc.repository.IProviderRepository;
import com.arka.micro_stock.domain.model.ProviderModel;
import com.arka.micro_stock.domain.spi.IProviderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProviderAdapter implements IProviderPersistencePort {

    private final IProviderRepository providerRepository;
    private final IProviderEntityMapper providerEntityMapper;

    @Override
    public Mono<Void> save(ProviderModel provider) {
        return providerRepository
                .save(providerEntityMapper.toEntity(provider))
                .then();
    }

    @Override
    public Mono<Boolean> existsByName(String name) {
        return providerRepository.findByName(name).hasElement();
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return providerRepository.findByEmail(email).hasElement();
    }

    @Override
    public Flux<ProviderModel> findAll() {
        return providerRepository.findAll()
                .map(providerEntityMapper::toModel);
    }



}
