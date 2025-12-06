package com.example.mybank.infrastructure.driven.jpa;

import com.example.mybank.domain.model.Stock;
import com.example.mybank.domain.ports.StockRepository;
import com.example.mybank.infrastructure.driven.jpa.entity.StockEntity;
import com.github.f4b6a3.ulid.Ulid;
import com.github.f4b6a3.ulid.UlidCreator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaStockRepository implements StockRepository {

    private final JpaStockSpringRepository jpaStockSpringRepository;

    public JpaStockRepository(JpaStockSpringRepository jpaStockSpringRepository) {
        this.jpaStockSpringRepository = jpaStockSpringRepository;
    }

    @Override
    public Stock save(Stock stock) {
        // Si l'Id est vide, on crée un ULID, sinon on utilise celui existant
        Ulid ulid = (stock.id().value() == null)
                ? UlidCreator.getMonotonicUlid()
                : stock.id().value();

        // Création de l'entité JPA à partir du domaine
        var entity = new StockEntity(
                ulid.toString(), // l'entité stocke l'ULID en String
                stock.symbol().value(),
                stock.companyName().value(),
                Long.valueOf(stock.price().valueCents()), // conversion en Long
                stock.currency().value(),
                stock.timestamp()
        );

        // Sauvegarde via Spring Data JPA
        StockEntity saved = jpaStockSpringRepository.save(entity);

        // Retourner le domaine reconstruit
        return toDomain(saved);
    }

    @Override
    public List<Stock> findAll() {
        return jpaStockSpringRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Stock> findById(Stock.Id id) {
        if (id == null || id.value() == null) {
            return Optional.empty();
        }
        return jpaStockSpringRepository.findById(id.value().toString())
                .map(this::toDomain);
    }

    @Override
    public List<Stock> findBySymbol(Stock.Symbol symbol) {
        return jpaStockSpringRepository.findBySymbol(symbol.value()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // Convertit une entité JPA en objet domaine
    private Stock toDomain(StockEntity entity) {
        return new Stock(
                new Stock.Id(Ulid.from(entity.getId())), // Convertir String en Ulid
                new Stock.Symbol(entity.getSymbol()),
                new Stock.CompanyName(entity.getCompanyName()),
                new Stock.Price(entity.getPriceCents()),
                new Stock.Currency(entity.getCurrency()),
                entity.getTimestamp()
        );
    }
}
