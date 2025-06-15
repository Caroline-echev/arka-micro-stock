package com.arka.micro_stock.adapters.driving.reactive.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventorySupplierResponse {
    private Long supplierId;
    private BigDecimal price;
    private Integer deliveryTime;
}