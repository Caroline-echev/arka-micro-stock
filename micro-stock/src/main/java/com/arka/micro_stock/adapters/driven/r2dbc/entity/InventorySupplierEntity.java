package com.arka.micro_stock.adapters.driven.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("tb_inventory_supplier")
public class InventorySupplierEntity {
    @Id
    private Long id;

    @Column("inventory_id")
    private Long inventoryId;

    @Column("supplier_id")
    private Long supplierId;

    private BigDecimal price;

    @Column("delivery_time")
    private Integer deliveryTime;
}
