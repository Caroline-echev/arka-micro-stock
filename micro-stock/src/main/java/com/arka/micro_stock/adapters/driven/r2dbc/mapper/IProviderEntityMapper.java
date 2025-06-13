package com.arka.micro_stock.adapters.driven.r2dbc.mapper;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.ProviderEntity;
import com.arka.micro_stock.domain.model.ProviderModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IProviderEntityMapper {

    ProviderEntity toEntity(ProviderModel model);

    ProviderModel toModel(ProviderEntity entity);
}
