package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String country;
    private String state;
    private String city;
    private String street;
    private String nomenclature;
    private String observation;
}
