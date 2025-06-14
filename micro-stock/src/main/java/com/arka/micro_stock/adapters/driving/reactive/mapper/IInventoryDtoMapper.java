package com.arka.micro_stock.adapters.driving.reactive.mapper;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventoryRequest;
import com.arka.micro_stock.domain.model.InventoryModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInventoryDtoMapper {

    InventoryModel toModel(InventoryRequest request);
}
