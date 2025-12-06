package com.example.mybank.infrastructure.driving.rest;

import com.example.mybank.domain.model.Stock;
import com.example.mybank.domain.usecase.stock.CreateStock;
import com.example.mybank.domain.usecase.stock.ListStocks;
import com.example.mybank.infrastructure.driving.rest.dto.CreateStockRequest;
import com.example.mybank.infrastructure.driving.rest.dto.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stocks")
@Tag(name = "Stock Quotes", description = "Gestion des cotations boursières")
public class StockController {

    private static final Logger log = LoggerFactory.getLogger(StockController.class);

    private final CreateStock createStock;
    private final ListStocks listStocks;

    public StockController(CreateStock createStock, ListStocks listStocks) {
        this.createStock = createStock;
        this.listStocks = listStocks;
    }

    @GetMapping
    @Operation(summary = "Lister toutes les cotations", description = "Récupère toutes les cotations boursières")
    public ResponseEntity<List<StockDTO>> listAll() {
        log.info("GET /api/stocks - Listing all stock quotes");

        List<Stock> stocks = listStocks.execute();
        List<StockDTO> dtos = stocks.stream()
                .map(StockDTO::fromDomain)
                .collect(Collectors.toList());

        log.info("Found {} stock quotes", dtos.size());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @Operation(summary = "Créer une cotation", description = "Ajoute une nouvelle cotation boursière")
    public ResponseEntity create(@Valid @RequestBody CreateStockRequest request) {
        log.info("POST /api/stocks - Creating stock quote: {}", request.symbol());

        Stock stock = createStock.execute(
                new Stock.Symbol(request.symbol()),
                new Stock.CompanyName(request.companyName()),
                new Stock.Price(request.priceCents()),
                new Stock.Currency(request.currency())
        );

        StockDTO dto = StockDTO.fromDomain(stock);
        log.info("Created stock quote with id: {}", dto.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}