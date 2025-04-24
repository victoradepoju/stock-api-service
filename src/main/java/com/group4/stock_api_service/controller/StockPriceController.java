package com.group4.stock_api_service.controller;


import com.group4.stock_api_service.model.StockPriceEntity;
import com.group4.stock_api_service.service.StockPriceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/stock-prices")
@RequiredArgsConstructor
public class StockPriceController {

    private final StockPriceQueryService stockPriceQueryService;

    @GetMapping("/symbols")
    public ResponseEntity<List<String>> getAllSymbols() {
        return ResponseEntity.ok(stockPriceQueryService.getAllAvailableSymbols());
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<List<StockPriceEntity>> getLatestPrices(@PathVariable String symbol) {
        return ResponseEntity.ok(stockPriceQueryService.getLatestPrices(symbol));
    }

    @GetMapping("/{symbol}/range")
    public ResponseEntity<List<StockPriceEntity>> getPricesInRange(
            @PathVariable String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime to) {
        return ResponseEntity.ok(stockPriceQueryService.getPricesInRange(symbol, from, to));
    }
}
