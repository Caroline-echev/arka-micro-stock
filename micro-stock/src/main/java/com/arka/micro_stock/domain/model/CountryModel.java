package com.arka.micro_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CountryModel {
    private Long id;
    private String name;
    private Long logisticsSupervisorId;
    private String status;
}
