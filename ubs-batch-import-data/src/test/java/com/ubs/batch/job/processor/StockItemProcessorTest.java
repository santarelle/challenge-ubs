package com.ubs.batch.job.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.batch.domain.entity.Stock;
import com.ubs.batch.domain.job.ItemProcessed;
import com.ubs.batch.domain.job.ItemRead;
import com.ubs.batch.fixture.StockFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StockItemProcessorTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenDuplicateItem_whenProcess_thenShouldRemoveDuplicitySuccessfully() throws Exception {
        StockItemProcessor stockItemProcessor = new StockItemProcessor(objectMapper);

        String givenFilename = "data_1.json";
        String givenJson = "{ \"data\": [ " +
                "{ \"product\": \"A\", \"quantity\": 1, \"price\": \"$2.50\", \"type\": \"XL\", \"industry\": \"X\", \"origin\": \"LA\" }, " +
                "{ \"product\": \"A\", \"quantity\": 1, \"price\": \"$2.50\", \"type\": \"XL\", \"industry\": \"X\", \"origin\": \"LA\" }, " +
                "{ \"product\": \"B\", \"quantity\": 2, \"price\": \"$2.50\", \"type\": \"XL\", \"industry\": \"X\", \"origin\": \"LA\" } " +
                "] }";
        ItemRead givenItemRead = ItemRead.builder().filename(givenFilename).json(givenJson).build();

        final ItemProcessed result = stockItemProcessor.process(givenItemRead);

        final int expectedStocks = 2;
        final Stock expectedStockA = StockFixture.productA();
        final Stock expectedStockB = StockFixture.productB();

        assertNotNull(result);
        assertEquals(expectedStocks, result.getStocks().size());
        assertThat(result.getStocks()).contains(expectedStockA);
        assertThat(result.getStocks()).contains(expectedStockB);
    }
}