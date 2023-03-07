package com.ubs.batch.job.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubs.batch.domain.entity.Stock;
import com.ubs.batch.domain.job.ItemProcessed;
import com.ubs.batch.domain.job.ItemRead;
import com.ubs.batch.domain.json.JsonData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockItemProcessor implements ItemProcessor<ItemRead, ItemProcessed> {

    private final ObjectMapper objectMapper;

    @Override
    public ItemProcessed process(ItemRead itemRead) throws Exception {
        log.debug("process(json)");
        final JsonData jsonData = objectMapper.readValue(itemRead.getJson(), JsonData.class);
        final Set<Stock> stocks = jsonData.getData().stream().parallel()
                .map(jsonItem -> Stock.builder()
                        .productName(jsonItem.getProduct())
                        .productType(jsonItem.getType())
                        .quantity(jsonItem.getQuantity())
                        .price(jsonItem.getPrice())
                        .volume(new BigDecimal(jsonItem.getQuantity()).multiply(jsonItem.getPrice()))
                        .industry(jsonItem.getIndustry())
                        .origin(jsonItem.getOrigin())
                        .build())
                .collect(Collectors.toSet());
        return ItemProcessed.builder()
                .filename(itemRead.getFilename())
                .stocks(stocks)
                .build();
    }
}
