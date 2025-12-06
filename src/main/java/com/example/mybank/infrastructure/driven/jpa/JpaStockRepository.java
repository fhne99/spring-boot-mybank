package com.example.mybank.infrastructure.driven.jpa;

import com.example.mybank.domain.model.Stock;
import com.example.mybank.domain.ports.StockRepository;
import com.example.mybank.infrastructure.driven.jpa.entity.StockEntity;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JpaStockRepository implements StockRepository {

    private final JpaStockSpringRepository jpaStockSpringRepository;

    public JpaStockRepository(JpaStockSpringRepository jpaStockSpringRepository) {
        this.jpaStockSpringRepository = jpaStockSpringRepository;
    }

    @Override
    public Stock save(Stock stock) {
        Ulid ulid = (stock.id().value() == null)
                ? UlidCreator.getMonotonicUlid()
                : stock.id().value();

        var entity = new StockEntity(
                ulid.toString(),
                stock.symbol().value(),
                stock.companyName().value(),
                Long.valueOf(stock.price().valueCents()),
                stock.currency().value(),
                stock.timestamp()
        );

        StockEntity saved = jpaStockSpringRepository.save(entity);

        return toDomain(saved);
    }

    @Override
    public List<Stock> findAll() {
        return jpaStockSpringRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Stock toDomain(StockEntity entity) {
        return new Stock(
                new Stock.Id(Ulid.from(entity.getId())),
                new Stock.Symbol(entity.getSymbol()),
                new Stock.CompanyName(entity.getCompanyName()),
                new Stock.Price(entity.getPriceCents()),
                new Stock.Currency(entity.getCurrency()),
                entity.getTimestamp()
        );
    }
}
