package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message = COUNTRY_REQUIRED)
    private String country;

    @NotBlank(message = STATE_REQUIRED)
    private String state;

    @NotBlank(message = CITY_REQUIRED)
    private String city;

    @NotBlank(message = STREET_REQUIRED)
    private String street;

    @NotBlank(message = NOMENCLATURE_REQUIRED)
    private String nomenclature;

    @Size(max = 255, message = OBSERVATION_TOO_LONG)
    private String observation;
}
