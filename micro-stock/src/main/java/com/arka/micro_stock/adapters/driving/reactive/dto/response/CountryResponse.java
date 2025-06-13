package com.arka.micro_stock.adapters.driving.reactive.dto.response;

import lombok.Data;

@Data
public class CountryResponse {
    private Long id;
    private String name;
    private Long logisticsSupervisorId;
    private String status;
}
