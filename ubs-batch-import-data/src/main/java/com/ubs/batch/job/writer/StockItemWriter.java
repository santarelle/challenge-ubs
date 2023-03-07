package com.ubs.batch.job.writer;

import com.ubs.batch.domain.entity.FileImport;
import com.ubs.batch.domain.job.ItemProcessed;
import com.ubs.batch.repository.FileImportRepository;
import com.ubs.batch.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockItemWriter implements ItemWriter<ItemProcessed> {

    private final StockRepository stockRepository;
    private final FileImportRepository fileImportRepository;

    @Override
    @Transactional
    public void write(List<? extends ItemProcessed> list) throws Exception {
        log.debug("write(list={})", list.size());
        if (!CollectionUtils.isEmpty(list)) {
            final ItemProcessed itemProcessed = list.get(0);
            final int stockInserted = stockRepository.saveAll(itemProcessed.getStocks()).size();
            log.debug("write(list={}) stockInserted={}", list.size(), stockInserted);
            fileImportRepository.save(FileImport.builder().name(itemProcessed.getFilename()).build());
        }
    }
}
