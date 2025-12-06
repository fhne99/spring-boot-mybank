package com.example.mybank.domain.model;

import com.github.f4b6a3.ulid.Ulid;
import java.time.Instant;
import java.util.Objects;

public record Stock(
        Id id,
        Symbol symbol,
        CompanyName companyName,
        Price price,
        Currency currency,
        Instant timestamp
) {
    public Stock {
        Objects.requireNonNull(id, "id is required");
        Objects.requireNonNull(symbol, "symbol is required");
        Objects.requireNonNull(companyName, "companyName is required");
        Objects.requireNonNull(price, "price is required");
        Objects.requireNonNull(currency, "currency is required");
        Objects.requireNonNull(timestamp, "timestamp is required");
    }

    public record Id(Ulid value) {
        public Id() {
            this(Ulid.fast());
        }

        public Id {
            if (value == null) throw new IllegalArgumentException("Id cannot be null");
        }

        public static Id from(String ulid) {
            return new Id(Ulid.from(ulid));
        }
    }

    public record Symbol(String value) {
        public Symbol {
            Objects.requireNonNull(value, "symbol value is required");
            if (value.isBlank()) {
                throw new IllegalArgumentException("symbol value cannot be blank");
            }
            if (value.length() > 10) {
                throw new IllegalArgumentException("symbol value cannot exceed 10 characters");
            }
        }
    }

    public record CompanyName(String value) {
        public CompanyName {
            Objects.requireNonNull(value, "companyName value is required");
            if (value.isBlank()) {
                throw new IllegalArgumentException("companyName value cannot be blank");
            }
            if (value.length() > 100) {
                throw new IllegalArgumentException("companyName value cannot exceed 100 characters");
            }
        }
    }

    public record Price(long valueCents) {
        public Price {
            if (valueCents < 0) {
                throw new IllegalArgumentException("price cannot be negative");
            }
        }

        public double toDecimal() {
            return valueCents / 100.0;
        }
    }

    public record Currency(String value) {
        public Currency {
            Objects.requireNonNull(value, "currency value is required");
            if (value.isBlank()) {
                throw new IllegalArgumentException("currency value cannot be blank");
            }
            if (!value.matches("^[A-Z]{3}$")) {
                throw new IllegalArgumentException("currency must be 3 uppercase letters (e.g., EUR, USD)");
            }
        }
    }
}