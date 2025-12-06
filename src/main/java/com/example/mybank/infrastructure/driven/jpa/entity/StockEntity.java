package com.example.mybank.infrastructure.driven.jpa.entity;

import com.example.mybank.domain.model.Stock;
import com.github.f4b6a3.ulid.Ulid;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "stocks")
public class StockEntity {

    @Id
    @Column(name = "id", nullable = false, length = 26)
    private String id;

    @Column(name = "symbol", nullable = false, length = 10)
    private String symbol;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "price_cents", nullable = false)
    private Long priceCents;

    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    @Column(name = "timestamp", nullable = false)
    private Instant timestamp;

    protected StockEntity() {}

    public StockEntity(String id, String symbol, String companyName, Long priceCents, String currency, Instant timestamp) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.priceCents = priceCents;
        this.currency = currency;
        this.timestamp = timestamp;
    }

    public Stock toDomain() {
        return new Stock(
                new Stock.Id(Ulid.from(id)),
                new Stock.Symbol(symbol),
                new Stock.CompanyName(companyName),
                new Stock.Price(priceCents),
                new Stock.Currency(currency),
                timestamp
        );
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public Long getPriceCents() { return priceCents; }
    public void setPriceCents(Long priceCents) { this.priceCents = priceCents; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}