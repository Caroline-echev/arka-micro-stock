package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InventorySupplierRequest {
    private Long supplierId;
    private BigDecimal price;
    private Integer deliveryTime;
}