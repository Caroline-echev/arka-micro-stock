package com.arka.micro_stock.domain.util.validation;

import com.arka.micro_stock.domain.model.InventoryHistoryModel;
import com.arka.micro_stock.domain.model.InventoryModel;
import com.arka.micro_stock.domain.model.InventorySupplierModel;
import com.arka.micro_stock.domain.model.UserInventoryHistoryModel;
import com.arka.micro_stock.domain.spi.IAuthenticationPersistencePort;
import com.arka.micro_stock.domain.spi.IHistoricalInventoryPersistencePort;
import com.arka.micro_stock.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoricalInventoryValidator {

    private final IAuthenticationPersistencePort authenticationPersistencePort;
    private final IUserPersistencePort userPersistence;
    private final IHistoricalInventoryPersistencePort historicalInventoryAdapter;

    public  Mono<Void> createTransaction(InventoryModel savedInventory,
                                              InventorySupplierModel savedSupplier,
                                              String exchangeType, String status) {

        return authenticationPersistencePort.getAuthenticatedEmail()
                .flatMap(email -> userPersistence.findByEmail(email))
                .map(user -> UserInventoryHistoryModel.builder()
                        .user_id(user.getId())
                        .role_user(user.getRoleId())
                        .build())
                .flatMap(userModel -> {
                    InventoryHistoryModel history = InventoryHistoryModel.builder()
                            .inventoryId(savedInventory.getId())
                            .createdAt(Instant.now().toString())
                            .price(savedSupplier.getPrice())
                            .quantity(savedInventory.getStockActual())
                            .exchangeType(exchangeType)
                            .status(status)
                            .user(userModel)
                            .build();

                    return historicalInventoryAdapter.save(history);
                }).then();
    }
}
