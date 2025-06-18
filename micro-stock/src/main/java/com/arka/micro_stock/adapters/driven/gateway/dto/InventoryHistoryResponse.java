package com.arka.micro_stock.adapters.driven.gateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class InventoryHistoryResponse {
    @JsonProperty("_id")
    private String id;

    @JsonProperty("inventory_id")
    private int inventoryId;

    @JsonProperty("created_at")
    private String createdAt;

    private double price;
    private int quantity;

    @JsonProperty("exchange_type")
    private String exchangeType;

    private String status;

}
