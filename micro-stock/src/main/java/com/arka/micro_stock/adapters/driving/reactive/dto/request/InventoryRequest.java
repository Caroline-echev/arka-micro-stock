package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class InventoryRequest {
    private Long productId;
    private Long countryId;
    private Integer stockActual;
    private Integer stockMinimo;
    private List<InventorySupplierRequest> suppliers;
}
