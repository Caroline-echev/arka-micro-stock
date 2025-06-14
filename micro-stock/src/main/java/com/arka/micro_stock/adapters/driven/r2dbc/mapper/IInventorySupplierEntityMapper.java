package com.arka.micro_stock.adapters.driven.r2dbc.mapper;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.InventorySupplierEntity;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventorySupplierEntityMapper {
    InventorySupplierEntity toEntity(InventorySupplierModel model);
    InventorySupplierModel toModel(InventorySupplierEntity entity);
}
