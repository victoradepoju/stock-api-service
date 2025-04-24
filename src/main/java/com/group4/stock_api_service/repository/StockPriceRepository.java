package com.group4.stock_api_service.repository;

import com.group4.stock_api_service.model.StockPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface StockPriceRepository extends JpaRepository<StockPriceEntity, UUID> {

    List<StockPriceEntity> findBySymbolOrderByDateDesc(String symbol);

    @Query("SELECT s FROM StockPriceEntity s WHERE s.symbol = :symbol AND s.date >= :from AND s.date <= :to ORDER BY s.date DESC")
    List<StockPriceEntity> findBySymbolAndDateBetween(
            @Param("symbol") String symbol,
            @Param("from") OffsetDateTime from,
            @Param("to") OffsetDateTime to);

    @Query("SELECT DISTINCT s.symbol FROM StockPriceEntity s")
    List<String> findAllDistinctSymbols();

    boolean existsBySymbolAndDate(String symbol, OffsetDateTime date);
}
