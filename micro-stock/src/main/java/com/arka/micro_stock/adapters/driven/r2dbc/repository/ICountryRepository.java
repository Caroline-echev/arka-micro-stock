package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.CountryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ICountryRepository extends ReactiveCrudRepository<CountryEntity, Long> {
    Mono<CountryEntity> findByName(String name);
    Mono<Boolean> existsByLogisticsSupervisorId (Long supervisorId);
    @Query("SELECT * FROM tb_country ORDER BY name ASC")
    Flux<CountryEntity> findAllByOrderByNameAsc();

}
