package com.arka.micro_stock.domain.util.validation;

import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static com.arka.micro_stock.domain.util.constants.CountryConstants.*;

@Slf4j
public class CountryValidator {

    public static Mono<Void> validateCountryName(String name, ICountryPersistencePort countryPersistence) {
        log.debug("Validating country name: {}", name);

        if (name == null || name.length() > 60) {
            log.warn("Validation failed - country name is null or exceeds 60 characters");
            return Mono.error(new BadRequestException(COUNTRY_NAME_LENGTH_ERROR));
        }

        return countryPersistence.existsByName(name)
                .doOnNext(exists -> {
                    if (exists) {
                        log.warn("Validation failed - country with name '{}' already exists", name);
                    } else {
                        log.debug("Country name '{}' is available", name);
                    }
                })
                .flatMap(exists -> exists ?
                        Mono.error(new DuplicateResourceException(COUNTRY_ALREADY_EXISTS)) :
                        Mono.empty());
    }

    public static Mono<Void> validateSupervisorExists(Long supervisorId, IUserPersistencePort userPersistence) {
        log.debug("Validating existence and role of supervisor with ID: {}", supervisorId);

        return userPersistence.existsByIdAndValidRole(supervisorId, ROLE_LOGISTIC)
                .doOnNext(exists -> {
                    if (exists) {
                        log.info("Supervisor with ID {} exists and has role {}", supervisorId, ROLE_LOGISTIC);
                    } else {
                        log.warn("Supervisor with ID {} does not exist or does not have role {}", supervisorId, ROLE_LOGISTIC);
                    }
                })
                .flatMap(exists -> exists ?
                        Mono.empty() :
                        Mono.error(new NotFoundException(SUPERVISOR_NOT_FOUND_OR_INVALID)));
    }

    public static Mono<Void> validateSupervisorNotAssigned(Long supervisorId, ICountryPersistencePort persistence) {
        log.debug("Validating that supervisor with ID {} is not assigned to any country", supervisorId);

        return persistence.existsByLogisticsSupervisorId(supervisorId)
                .doOnNext(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        log.warn("Supervisor with ID {} is already assigned to a country", supervisorId);
                    } else {
                        log.debug("Supervisor with ID {} is not assigned to any country", supervisorId);
                    }
                })
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new BadRequestException(SUPERVISOR_ALREADY_ASSIGNED));
                    }
                    return Mono.empty();
                });
    }
}
