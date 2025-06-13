package com.arka.micro_stock.adapters.driving.reactive.dto.request;

import com.arka.micro_stock.adapters.driving.reactive.dto.request.AddressRequest;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderRequest {

    @NotBlank
    @Pattern(regexp = "\\d+", message = "NIT must be numeric")
    private String nit;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String description;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\+\\d{1,15}$", message = "Phone must start with + and contain up to 16 digits including the prefix")
    private String phone;

    private AddressRequest address;

    @NotBlank
    private String website;

    @NotBlank
    private String contactName;
}
