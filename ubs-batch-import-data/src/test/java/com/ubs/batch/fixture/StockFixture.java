package com.ubs.batch.fixture;

import com.ubs.batch.domain.entity.Stock;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

public final class StockFixture {

    private StockFixture() {
    }

    public static Stock productA() {
        return Stock.builder()
                .productName("A")
                .quantity(1)
                .price(new BigDecimal("2.50"))
                .volume(new BigDecimal("2.50"))
                .productType("XL")
                .industry("X")
                .origin("LA")
                .build();
    }

    public static Stock productB() {
        return Stock.builder()
                .productName("B")
                .quantity(2)
                .price(new BigDecimal("2.50"))
                .volume(new BigDecimal("5.00"))
                .productType("XL")
                .industry("X")
                .origin("LA")
                .build();
    }

    public static Set<Stock> list() {
        final LinkedHashSet<Stock> stocks = new LinkedHashSet<>();
        stocks.add(productA());
        stocks.add(productB());
        return stocks;
    }
}
