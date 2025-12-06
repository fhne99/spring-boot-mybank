package com.example.mybank.infrastructure.driving.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateStockRequest(
        @NotBlank(message = "Symbol is required")
        @Pattern(regexp = "^[A-Z0-9]{1,10}$", message = "Symbol must be 1-10 uppercase letters/numbers")
        String symbol,

        @NotBlank(message = "Company name is required")
        String companyName,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be positive or zero")
        Long priceCents,

        @NotBlank(message = "Currency is required")
        @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be 3 uppercase letters (e.g., EUR, USD)")
        String currency
) { }
