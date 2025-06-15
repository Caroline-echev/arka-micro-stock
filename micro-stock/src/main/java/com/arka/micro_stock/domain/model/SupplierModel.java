package com.arka.micro_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierModel {
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
