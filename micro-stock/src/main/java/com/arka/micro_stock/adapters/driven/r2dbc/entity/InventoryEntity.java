package com.arka.micro_stock.adapters.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tb_inventory")
public class InventoryEntity {
    @Id
    private Long id;

    @Column("product_id")
    private Long productId;

    @Column("country_id")
    private Long countryId;

    @Column("stock_actual")
    private Integer stockActual;

    @Column("stock_minimo")
    private Integer stockMinimo;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
