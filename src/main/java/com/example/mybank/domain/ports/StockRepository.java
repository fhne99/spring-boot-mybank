package com.example.mybank.domain.ports;

import com.example.mybank.domain.model.Stock;
import java.util.List;
import java.util.Optional;

public interface StockRepository {
    Stock save(Stock stock);
    List findAll();
    Optional findById(Stock.Id id);
    List findBySymbol(Stock.Symbol symbol);
}
