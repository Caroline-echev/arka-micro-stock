package com.arka.micro_stock.adapters.driven.r2dbc.repository;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.InventorySupplierEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface IInventorySupplierRepository extends ReactiveCrudRepository<InventorySupplierEntity, Long> {
}
