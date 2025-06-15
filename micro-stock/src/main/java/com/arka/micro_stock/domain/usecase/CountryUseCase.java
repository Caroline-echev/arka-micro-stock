package com.arka.micro_stock.domain.usecase;

import com.arka.micro_stock.domain.api.ICountryServicePort;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.model.CountryModel;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
import com.arka.micro_stock.domain.util.validation.CountryValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.arka.micro_stock.domain.util.constants.CountryConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryUseCase implements ICountryServicePort {

    private final ICountryPersistencePort countryPersistence;
    private final IUserPersistencePort userPersistence;

    @Override
    public Mono<Void> createCountry(CountryModel country) {
        log.info("Creating country with name: {}", country.getName());
        return Mono.just(country)
                .doOnNext(c -> c.setStatus(COUNTRY_STATUS_ACTIVE))
                .flatMap(c -> CountryValidator.validateCountryName(c.getName(), countryPersistence))
                .then(Mono.defer(() -> CountryValidator.validateSupervisorExists(
                        country.getLogisticsSupervisorId(),
                        userPersistence
                )))
                .then(Mono.defer(() -> CountryValidator.validateSupervisorNotAssigned(
                        country.getLogisticsSupervisorId(),
                        countryPersistence
                )))
                .then(Mono.defer(() -> {
                    log.debug("Saving country: {}", country.getName());
                    return countryPersistence.saveCountry(country);
                }));
    }

    @Override
    public Mono<CountryModel> getCountryById(Long id) {
        log.info("Fetching country by id: {}", id);
        return countryPersistence.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(COUNTRY_NOT_FOUND)));
    }

    @Override
    public Flux<CountryModel> getAllCountries() {
        log.info("Fetching all countries");
        return countryPersistence.findAllOrderByNameAsc();
    }

    @Override
    public Mono<Void> updateCountry(Long id, CountryModel countryModel) {
        log.info("Updating country with id: {}", id);
        return countryPersistence.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException(COUNTRY_NOT_FOUND)))
                .flatMap(existing -> {
                    countryModel.setId(id);
                    return CountryValidator.validateCountryName(countryModel.getName(), countryPersistence);
                })
                .then(Mono.defer(() -> CountryValidator.validateSupervisorExists(
                        countryModel.getLogisticsSupervisorId(),
                        userPersistence
                )))
                .then(Mono.defer(() -> CountryValidator.validateSupervisorNotAssigned(
                        countryModel.getLogisticsSupervisorId(),
                        countryPersistence
                )))
                .then(Mono.defer(() -> {
                    log.debug("Saving updated country: {}", countryModel.getName());
                    return countryPersistence.saveCountry(countryModel);
                }));
    }
}
