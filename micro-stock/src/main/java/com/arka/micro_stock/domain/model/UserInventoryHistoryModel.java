package com.arka.micro_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserInventoryHistoryModel {
    private Long user_id;
    private Long role_user;
}
