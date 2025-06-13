package com.arka.micro_stock.adapters.driving.reactive.mapper;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.CountryRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.CountryResponse;
import com.arka.micro_stock.domain.model.CountryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICountryDtoMapper {
    @Mapping(target = "id", ignore = true)
    CountryModel toModel(CountryRequest request);

    CountryResponse toResponse(CountryModel model);
}
