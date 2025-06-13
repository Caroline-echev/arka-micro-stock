package com.arka.micro_stock.domain.util.validation;

import com.arka.micro_stock.domain.exception.BadRequestException;
import com.arka.micro_stock.domain.exception.DuplicateResourceException;
import com.arka.micro_stock.domain.exception.NotFoundException;
import com.arka.micro_stock.domain.spi.ICountryPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
import reactor.core.publisher.Mono;

import static com.arka.micro_stock.domain.util.constants.CountryConstants.*;

public class CountryValidator {
    public static Mono<Void> validateCountryName(String name, ICountryPersistencePort countryPersistence) {
        if (name == null || name.length() > 60) {
            return Mono.error(new BadRequestException(COUNTRY_NAME_LENGTH_ERROR));
        }
        return countryPersistence.existsByName(name)
                .flatMap(exists -> exists ?
                        Mono.error(new DuplicateResourceException(COUNTRY_ALREADY_EXISTS)) :
                        Mono.empty());
    }

    public static Mono<Void> validateSupervisorExists(Long supervisorId, IUserPersistencePort userPersistence) {
        return userPersistence.existsByIdAndValidRole(supervisorId, "LOGISTIC")
                .flatMap(exists -> exists ?
                        Mono.empty() :
                        Mono.error(new NotFoundException(SUPERVISOR_NOT_FOUND_OR_INVALID)));
    }

    public static Mono<Void> validateSupervisorNotAssigned(Long supervisorId, ICountryPersistencePort persistence) {
        return persistence.existsByLogisticsSupervisorId(supervisorId)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new BadRequestException("Supervisor is already assigned to a country"));
                    }
                    return Mono.empty();
                });
    }

}