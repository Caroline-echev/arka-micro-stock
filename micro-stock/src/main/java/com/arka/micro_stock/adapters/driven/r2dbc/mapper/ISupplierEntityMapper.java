package com.arka.micro_stock.adapters.driven.r2dbc.mapper;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.SupplierEntity;
import com.arka.micro_stock.domain.model.SupplierModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISupplierEntityMapper {

    SupplierEntity toEntity(SupplierModel model);

    SupplierModel toModel(SupplierEntity entity);
}
