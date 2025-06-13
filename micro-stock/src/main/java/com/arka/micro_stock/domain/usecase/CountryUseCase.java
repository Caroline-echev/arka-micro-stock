package com.arka.micro_stock.domain.usecase;


import com.arka.micro_stock.domain.api.ICountryServicePort;
import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.CountryModel;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
import com.arka.micro_stock.domain.util.validation.CountryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CountryUseCase implements ICountryServicePort {

    private final ICountryPersistencePort countryPersistence;
    private final IUserPersistencePort userPersistence;
    @Override
    public Mono<Void> createCountry(CountryModel country) {
        return Mono.just(country)
                .doOnNext(c -> c.setStatus("ACTIVE"))
                .flatMap(c -> CountryValidator.validateCountryName(c.getName(), countryPersistence))
                .then(Mono.defer(() -> CountryValidator.validateSupervisorExists(
                        country.getLogisticsSupervisorId(),
                        userPersistence
                )))
                .then(Mono.defer(() -> CountryValidator.validateSupervisorNotAssigned(
                        country.getLogisticsSupervisorId(),
                        countryPersistence
                )))
                .then(Mono.defer(() -> countryPersistence.saveCountry(country)));
    }

    @Override
    public Mono<CountryModel> getCountryById(Long id) {
        return countryPersistence.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Country not found")));
    }

    @Override
    public Flux<CountryModel> getAllCountries() {
        return countryPersistence.findAllOrderByNameAsc();
    }



}

