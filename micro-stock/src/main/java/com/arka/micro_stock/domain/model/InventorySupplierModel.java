package com.arka.micro_stock.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class InventorySupplierModel {
    private Long inventoryId;
    private Long supplierId;
    private BigDecimal price;
    private Integer deliveryTime;
}
