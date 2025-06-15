package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.arka.micro_stock.adapters.util.CountryConstantsDriving.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest {

    @NotBlank(message = NIT_REQUIRED)
    @Pattern(regexp = REGEX_NUMERIC, message = NIT_NUMERIC)
    private String nit;

    @NotBlank(message = NAME_REQUIRED)
    @Size(max = NAME_MAX_SIZE, message = NAME_MAX_LENGTH)
    private String name;

    @NotBlank(message = DESCRIPTION_REQUIRED)
    @Size(max = DESCRIPTION_MAX_SIZE, message = DESCRIPTION_MAX_LENGTH)
    private String description;

    @NotBlank(message = EMAIL_REQUIRED)
    @Email(message = EMAIL_INVALID)
    private String email;

    @NotBlank(message = PHONE_REQUIRED)
    @Pattern(regexp = REGEX_PHONE, message = PHONE_INVALID)
    private String phone;

    @Valid
    private AddressRequest address;

    @NotBlank(message = WEBSITE_REQUIRED)
    private String website;

    @NotBlank(message = CONTACT_NAME_REQUIRED)
    private String contactName;
}
