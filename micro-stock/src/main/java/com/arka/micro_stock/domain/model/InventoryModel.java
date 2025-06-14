package com.arka.micro_stock.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InventoryModel {
    private Long id;
    private Long productId;
    private Long countryId;
    private Integer stockActual;
    private Integer stockMinimo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InventorySupplierModel> suppliers;
}

