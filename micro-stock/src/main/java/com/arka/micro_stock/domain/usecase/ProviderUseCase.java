package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.IProviderServicePort;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.model.ProviderModel;
import com.arka.micro_stock.domain.spi.IProviderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class ProviderUseCase implements IProviderServicePort {

    private final IProviderPersistencePort providerPersistencePort;

    @Override
    public Mono<Void> createProvider(ProviderModel provider) {
        return validateUniqueNameAndEmail(provider)
                .then(providerPersistencePort.save(provider));
    }


    @Override
    public Flux<ProviderModel> getProviders( String country) {
        return providerPersistencePort.findAll()
                .filter(provider -> country == null ||
                        (provider.getAddress() != null && provider.getAddress().toLowerCase().contains(country.toLowerCase())))
                .sort(Comparator.comparing(ProviderModel::getName));
    }

    private Mono<Void> validateUniqueNameAndEmail(ProviderModel provider) {
        return providerPersistencePort.existsByName(provider.getName())
                .flatMap(existsByName -> {
                    if (existsByName) {
                        return Mono.error(new DuplicateResourceException("Provider name already exists"));
                    }
                    return Mono.empty();
                })
                .then(providerPersistencePort.existsByEmail(provider.getEmail()))
                .flatMap(existsByEmail -> {
                    if (existsByEmail) {
                        return Mono.error(new DuplicateResourceException("Provider email already exists"));
                    }
                    return Mono.empty();
                });
    }
}
