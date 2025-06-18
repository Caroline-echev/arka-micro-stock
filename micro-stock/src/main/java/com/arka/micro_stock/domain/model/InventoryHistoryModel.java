package com.arka.micro_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder

public class InventoryHistoryModel {

    private String id;
    private Long inventoryId;
    private String createdAt;
    private BigDecimal price;
    private Integer quantity;
    private String exchangeType;
    private String status;
    private UserInventoryHistoryModel user;

}
