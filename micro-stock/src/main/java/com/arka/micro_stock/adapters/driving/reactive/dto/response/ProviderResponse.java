package com.arka.micro_stock.adapters.driving.reactive.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderResponse {
    private String nit;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;
    private String website;
}
