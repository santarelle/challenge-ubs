package com.ubs.api.fixture;


import com.ubs.api.domain.entity.Stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public final class StockFixture {

    private StockFixture() {
    }

    public static Stock product(String name, int quantity, BigDecimal price) {
        return Stock.builder()
                .productName(name)
                .quantity(quantity)
                .price(price)
                .volume(price.multiply(BigDecimal.valueOf(quantity)).setScale(2, RoundingMode.HALF_EVEN))
                .productType("XL")
                .industry("X")
                .origin("LA")
                .build();
    }

}
