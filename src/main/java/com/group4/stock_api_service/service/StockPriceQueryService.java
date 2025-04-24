package com.group4.stock_api_service.service;

import com.group4.stock_api_service.model.StockPriceEntity;
import com.group4.stock_api_service.repository.StockPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPriceQueryService {

    private final StockPriceRepository stockPriceRepository;

    public List<StockPriceEntity> getLatestPrices(String symbol) {
        return stockPriceRepository.findBySymbolOrderByDateDesc(symbol);
    }

    public List<StockPriceEntity> getPricesInRange(String symbol, OffsetDateTime from, OffsetDateTime to) {
        return stockPriceRepository.findBySymbolAndDateBetween(symbol, from, to);
    }

    public List<String> getAllAvailableSymbols() {
        return stockPriceRepository.findAllDistinctSymbols();
    }
}
