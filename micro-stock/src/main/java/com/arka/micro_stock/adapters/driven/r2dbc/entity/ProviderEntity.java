package com.arka.micro_stock.adapters.driven.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("tb_providers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderEntity {

    @Id
    private Long id;

    private String nit;
    private String name;
    private String description;
    private String email;
    private String phone;
    private String address;
    private String website;
    @Column("contact_name")
    private String contactName;
}
