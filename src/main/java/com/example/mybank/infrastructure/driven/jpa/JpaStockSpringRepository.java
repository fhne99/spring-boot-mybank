package com.example.mybank.infrastructure.driven.jpa;

import com.example.mybank.infrastructure.driven.jpa.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaStockSpringRepository extends JpaRepository<StockEntity, String> {
    List<StockEntity> findBySymbol(String symbol);
}
