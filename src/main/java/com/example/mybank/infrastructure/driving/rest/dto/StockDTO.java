package com.example.mybank.infrastructure.driving.rest.dto;

import com.example.mybank.domain.model.Stock;

import java.time.Instant;

public record StockDTO(
        String id,
        String symbol,
        String companyName,
        double price,
        long priceCents,
        String currency,
        Instant timestamp
) {
    public static StockDTO fromDomain(Stock stock) {
        return new StockDTO(
                stock.id().value().toString(),
                stock.symbol().value(),
                stock.companyName().value(),
                stock.price().toDecimal(),
                stock.price().valueCents(),
                stock.currency().value(),
                stock.timestamp()
        );
    }
}