package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.InventorySupplierEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface IInventorySupplierRepository extends ReactiveCrudRepository<InventorySupplierEntity, Long>{

    @Query("select * from tb_inventory_supplier where inventory_id = :inventoryId")
    Flux<InventorySupplierEntity> findByInventoryId (Long inventoryId);
}
