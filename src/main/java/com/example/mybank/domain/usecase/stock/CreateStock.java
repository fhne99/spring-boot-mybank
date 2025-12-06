package com.example.mybank.domain.usecase.stock;

import com.example.mybank.domain.model.Stock;
import com.example.mybank.domain.ports.StockRepository;

import java.time.Instant;
import java.util.Objects;

public class CreateStock {

    private final StockRepository stockRepository;

    public CreateStock(StockRepository stockRepository) {
        this.stockRepository = Objects.requireNonNull(stockRepository);
    }

    public Stock execute(Stock.Symbol symbol, Stock.CompanyName companyName,
                         Stock.Price price, Stock.Currency currency) {
        var stock = new Stock(
                new Stock.Id(),
                symbol,
                companyName,
                price,
                currency,
                Instant.now()
        );

        return stockRepository.save(stock);
    }
}
