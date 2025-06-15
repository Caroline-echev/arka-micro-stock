package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.*;

@Data
public class InventorySupplierRequest {

    @NotNull(message = SUPPLIER_ID_REQUIRED)
    private Long supplierId;

    @NotNull(message = PRICE_REQUIRED)
    @DecimalMin(value = "0.0", inclusive = true, message = PRICE_MIN)
    private BigDecimal price;

    @NotNull(message = DELIVERY_TIME_REQUIRED)
    @Min(value = 0, message = DELIVERY_TIME_MIN)
    private Integer deliveryTime;
}
