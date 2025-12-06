package com.example.mybank.domain.usecase.stock;

import com.example.mybank.domain.model.Stock;
import com.example.mybank.domain.ports.StockRepository;
import java.util.List;
import java.util.Objects;

public class ListStocks {

    private final StockRepository stockRepository;

    public ListStocks(StockRepository stockQuoteRepository) {
        this.stockRepository = Objects.requireNonNull(stockQuoteRepository);
    }

    public List execute() {
        return stockRepository.findAll();
    }
}
