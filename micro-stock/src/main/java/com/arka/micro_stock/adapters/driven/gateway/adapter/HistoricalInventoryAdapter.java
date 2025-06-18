package com.arka.micro_stock.adapters.driven.gateway.adapter;

import com.arka.micro_stock.domain.model.InventoryHistoryModel;
import com.arka.micro_stock.domain.spi.IHistoricalInventoryPersistencePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class HistoricalInventoryAdapter implements IHistoricalInventoryPersistencePort {

    private static final String API_BASE_URL = "https://5w66q2wovd.execute-api.us-east-2.amazonaws.com/prod";
    private static final String BASE_ENDPOINT = "/historical-inventory";

    private final WebClient webClient;

    public HistoricalInventoryAdapter(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(API_BASE_URL)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Override
    public Mono<InventoryHistoryModel> save(InventoryHistoryModel history) {
        return webClient.post()
                .uri(BASE_ENDPOINT)
                .bodyValue(history)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(error -> new RuntimeException(
                                        "Error saving history: "  + error))
                )
                .bodyToMono(InventoryHistoryModel.class)
                .timeout(Duration.ofSeconds(30));
    }


}
