package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.*;

@Data
public class InventoryRequest {

    @NotNull(message = PRODUCT_ID_REQUIRED)
    private Long productId;

    @NotNull(message = COUNTRY_ID_REQUIRED)
    private Long countryId;

    @NotNull(message = STOCK_ACTUAL_REQUIRED)
    @Min(value = 0, message = "Actual stock must be zero or positive")
    private Integer stockActual;

    @NotNull(message = STOCK_MIN_REQUIRED)
    @Min(value = 0, message = "Minimum stock must be zero or positive")
    private Integer stockMinimo;

    @NotEmpty(message = SUPPLIERS_REQUIRED)
    private List<InventorySupplierRequest> suppliers;
}
