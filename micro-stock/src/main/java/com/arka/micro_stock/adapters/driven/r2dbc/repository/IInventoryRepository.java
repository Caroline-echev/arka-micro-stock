package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.InventoryEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IInventoryRepository extends ReactiveCrudRepository<InventoryEntity, Long> {

    @Query("select * from tb_inventory where product_id = :productId and country_id = :countryId")
    Mono<Boolean> existsByProductIdAndCountryId (Long productId, Long countryId);

}
