package com.ubs.batch.job.writer;

import com.ubs.batch.domain.entity.FileImport;
import com.ubs.batch.domain.entity.Stock;
import com.ubs.batch.domain.job.ItemProcessed;
import com.ubs.batch.fixture.StockFixture;
import com.ubs.batch.repository.FileImportRepository;
import com.ubs.batch.repository.StockRepository;
import liquibase.repackaged.org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
class StockItemWriterTest {

    @InjectMocks
    private StockItemWriter stockItemWriter;

    @Mock
    private StockRepository stockRepository;

    @Mock
    private FileImportRepository fileImportRepository;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenListStocks_whenWrite_then_ShouldCallStockAndFileImportRepositoriesSaveMethods() throws Exception {

        String givenFilename = "data_1.json";
        Set<Stock> givenStocks = StockFixture.list();
        ItemProcessed givenItemProcessed = ItemProcessed.builder().filename(givenFilename).stocks(givenStocks).build();
        List<ItemProcessed> givenItemProcessedList = Collections.singletonList(givenItemProcessed);
        FileImport givenFileImport = FileImport.builder().name(givenFilename).build();

        when(stockRepository.saveAll(givenStocks))
                .thenReturn(givenStocks.stream()
                        .peek(s -> s.setId(RandomUtils.nextLong()))
                        .collect(Collectors.toList()));

        when(fileImportRepository.save(givenFileImport)).thenReturn(givenFileImport);

        stockItemWriter.write(givenItemProcessedList);

        verify(stockRepository, times(1)).saveAll(givenStocks);
        verify(fileImportRepository, times(1)).save(givenFileImport);
    }

    @Test
    void givenEmptyList_whenWrite_thenShouldNotCallStockAndFileImportRepositoriesSaveMethods() throws Exception {

        List<ItemProcessed> givenItemProcessedList = Collections.emptyList();

        stockItemWriter.write(givenItemProcessedList);

        verifyNoInteractions(stockRepository);
        verifyNoInteractions(fileImportRepository);
    }

    @Test
    void givenNull_whenWrite_thenShouldThrowExceptionSuccessfully() throws Exception {

        assertThrows(Exception.class, () -> stockItemWriter.write(null));

        verifyNoInteractions(stockRepository);
        verifyNoInteractions(fileImportRepository);
    }
}