package com.group4.stock_api_service.service;

import com.group4.stock_api_service.model.StockPriceEntity;
import com.group4.stock_api_service.repository.StockPriceRepository;
import com.group4.stock_fetcher.model.StockPriceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockPriceConsumerService {

    private final StockPriceRepository stockPriceRepository;

    @KafkaListener(topics = "${app.kafka.topic}", groupId = "stock-price-group")
    @Transactional
    public void consumeStockPrice(StockPriceEvent stockPriceEvent) {
        StockPriceEntity entity = convertToEntity(stockPriceEvent);

        boolean exists = stockPriceRepository.existsBySymbolAndDate(
                entity.getSymbol(),
                entity.getDate());

        if (!exists) {
            stockPriceRepository.save(entity);
            log.info("Saved stock price: {} - {}", entity.getSymbol(), entity.getDate());
        } else {
            log.debug("Skipping duplicate stock price: {} - {}", entity.getSymbol(), entity.getDate());
        }
    }

    private StockPriceEntity convertToEntity(StockPriceEvent event) {
        StockPriceEntity entity = new StockPriceEntity();
        entity.setSymbol(event.getSymbol());
        entity.setDate(event.getDate());
        entity.setOpen(event.getOpen());
        entity.setHigh(event.getHigh());
        entity.setLow(event.getLow());
        entity.setClose(event.getClose());
        entity.setVolume(event.getVolume());
        entity.setExchange(event.getExchange());
        return entity;
    }
}
