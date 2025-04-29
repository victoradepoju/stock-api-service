# Stock API Service

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1.0-green)
![Kafka](https://img.shields.io/badge/Apache_Kafka-3.4.0-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15.0-blue)

## Overview

The Stock API Service is a Spring Boot application that:
- Consumes stock price events from Kafka
- Stores them in PostgreSQL database
- Provides REST API endpoints for querying stock price data

## Features

- **Real-time Data Processing**: Consumes stock price events from Kafka in real-time
- **REST API**: Provides endpoints for querying stock prices by symbol and date range
- **Data Persistence**: Stores stock price data in PostgreSQL with proper indexing
- **Duplicate Prevention**: Skips duplicate stock price entries
- **Scalable Architecture**: Ready for horizontal scaling

## Project Structure

```
📦 stock-api-service
├── 📂 config
│   └── 📄 KafkaConsumerConfig.java       # Kafka consumer configuration
├── 📂 controller
│   └── 📄 StockPriceController.java      # REST API endpoints
├── 📂 model
│   └── 📄 StockPriceEntity.java          # JPA entity for stock prices
├── 📂 repository
│   └── 📄 StockPriceRepository.java      # JPA repository for StockPriceEntity
├── 📂 service
│   ├── 📄 StockPriceConsumerService.java # Kafka message consumer
│   └── 📄 StockPriceQueryService.java    # Database query service
└── 📄 StockApiServiceApplication.java    # Main application class
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/stock-prices/symbols` | Get all available stock symbols |
| GET | `/api/stock-prices/{symbol}` | Get latest prices for a specific symbol |
| GET | `/api/stock-prices/{symbol}/range` | Get prices within date range |

### Example Requests

**Get all symbols:**
```bash
curl http://localhost:8082/api/stock-prices/symbols
```

**Get latest prices for AAPL:**
```bash
curl http://localhost:8082/api/stock-prices/AAPL
```

**Get prices in date range:**
```bash
curl "http://localhost:8082/api/stock-prices/AAPL/range?from=2023-01-01T00:00:00Z&to=2023-01-31T23:59:59Z"
```

## Configuration

### Environment Variables

| Variable | Description | Example |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC URL | `jdbc:postgresql://localhost:5432/stock-db` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `stockuser` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `stockpass` |
| `SPRING_KAFKA_BOOTSTRAP_SERVERS` | Kafka bootstrap servers | `localhost:29092` |

## Prerequisites

- Java 17+
- Apache Kafka
- Zookeeper
- PostgreSQL 15+
- Maven

## Setup Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/victoradepoju/stock-api-service.git
   cd stock-api-service
   ```

2. **Create database**:
   ```sql
   CREATE DATABASE "stock-db";
   CREATE USER stockuser WITH PASSWORD 'stockpass';
   GRANT ALL PRIVILEGES ON DATABASE "stock-db" TO stockuser;
   ```

3. **Configure environment**:
   ```bash
   export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/stock-db
   export SPRING_DATASOURCE_USERNAME=stockuser
   export SPRING_DATASOURCE_PASSWORD=stockpass
   ```

4. **Build and run**:
   ```bash
   mvn spring-boot:run
   ```

## Kafka Integration

The service consumes messages from the `stock-prices` topic with:
- Consumer group: `stock-price-group`
- JSON deserialization for `StockPriceEvent` objects
- Auto-offset reset to `earliest`
- Duplicate detection based on symbol and date

## Database Schema

The `stock_prices` table contains:
- UUID primary key
- Stock symbol (indexed)
- Price data (open, high, low, close, volume)
- Timestamps (date, created_at, updated_at)

## Monitoring

The service logs:
- Successful message consumption
- Database operations
- API requests
- Error conditions
 
