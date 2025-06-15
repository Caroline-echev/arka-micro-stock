package com.arka.micro_stock.adapters.driven.r2dbc.mapper;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.InventoryEntity;
import com.arka.micro_stock.domain.model.InventoryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryEntityMapper {
    InventoryEntity toEntity(InventoryModel model);
    InventoryModel toModel(InventoryEntity entity);

}
