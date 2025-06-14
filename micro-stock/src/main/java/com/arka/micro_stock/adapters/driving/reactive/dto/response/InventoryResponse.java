package com.arka.micro_stock.adapters.driving.reactive.dto.response;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.InventorySupplierRequest;
import lombok.Data;

import java.util.List;

@Data
public class InventoryResponse {
    private Long id;
    private Long productId;
    private Long countryId;
    private Integer stockActual;
    private Integer stockMinimo;
    private List<InventorySupplierResponse> suppliers;
}
