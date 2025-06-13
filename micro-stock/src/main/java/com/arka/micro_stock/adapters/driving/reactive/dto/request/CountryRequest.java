package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.*;

@Data
@AllArgsConstructor
public class CountryRequest {

    @NotBlank(message = NAME_NOT_BLANK)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_SIZE_MESSAGE)
    private String name;

    @NotNull(message = DESCRIPTION_NOT_BLANK)
    private Long logisticsSupervisorId;

}
