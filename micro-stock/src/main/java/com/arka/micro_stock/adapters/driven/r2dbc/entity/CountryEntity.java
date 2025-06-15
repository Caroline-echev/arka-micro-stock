package com.arka.micro_stock.adapters.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("tb_country")
public class CountryEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("logistics_supervisor_id")
    private Long logisticsSupervisorId;

    @Column("status")
    private String status;

}
