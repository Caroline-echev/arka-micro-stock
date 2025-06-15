package com.arka.micro_stock.adapters.driven.r2dbc.mapper;

import com.arka.micro_stock.adapters.driven.r2dbc.entity.CountryEntity;
import com.arka.micro_stock.domain.model.CountryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICountryEntityMapper {

    CountryEntity toEntity(CountryModel model);

    CountryModel toModel(CountryEntity entity);
}