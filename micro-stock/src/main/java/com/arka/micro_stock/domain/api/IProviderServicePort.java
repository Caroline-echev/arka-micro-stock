package com.arka.micro_stock.domain.api;

import com.arka.micro_stock.domain.model.ProviderModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProviderServicePort {
    Mono<Void> createProvider(ProviderModel providerModel);
    Flux<ProviderModel> getProviders( String country);
    Mono<ProviderModel> getProviderByName(String name);
}
