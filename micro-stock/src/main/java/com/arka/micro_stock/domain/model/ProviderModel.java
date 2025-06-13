package com.arka.micro_stock.domain.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderModel {
    private Long id;
    private String nit;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String website;
    private String contactName;
    private String address;
}
