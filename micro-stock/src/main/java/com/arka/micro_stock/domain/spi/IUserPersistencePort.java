package com.arka.micro_stock.domain.spi;

import com.arka.micro_stock.domain.model.UserModel;
import reactor.core.publisher.Mono;

public interface IUserPersistencePort {

    Mono<Boolean> existsByIdAndValidRole(Long supervisorId, String role);
    Mono<UserModel> findByEmail(String email);
}
