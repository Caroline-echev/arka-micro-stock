package com.arka.micro_stock.adapters.driving.reactive.mapper;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventoryRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventorySupplierRequest;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.InventoryResponse;
import com.arka.micro_stock.adapters.driving.reactive.dto.response.InventorySupplierResponse;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IInventoryDtoMapper {

    InventoryModel toModel(InventoryRequest request);

    InventoryResponse toResponse(InventoryModel model);

    InventorySupplierResponse toResponse(InventorySupplierModel model);

    List<InventorySupplierResponse> toSupplierResponseList(List<InventorySupplierModel> modelList);
    List<InventorySupplierModel> toSupplierModelList(List<InventorySupplierRequest> responseList);

}
