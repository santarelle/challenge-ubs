package com.ubs.batch.job.reader;

import com.ubs.batch.domain.entity.FileImport;
import com.ubs.batch.domain.job.ItemRead;
import com.ubs.batch.repository.FileImportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockItemReaderTest {

    private StockItemReader stockItemReader;

    private FileImportRepository fileImportRepository;


    @BeforeEach
    public void initMocks() {
        fileImportRepository = Mockito.mock(FileImportRepository.class);

    }

    @Test
    void givenJson_whenRead_thenShouldLoadResourceAndFileNameSuccessfully() throws Exception {
        Resource givenResource = new ClassPathResource("fixture/data_test.json");
        Resource[] givenResources = singletonList(givenResource).toArray(new Resource[0]);

        stockItemReader = new StockItemReader(givenResources, fileImportRepository);

        when(fileImportRepository.findByName("data_test.json")).thenReturn(Optional.empty());

        final ItemRead result = stockItemReader.read();

        String expectedJson = "{\"data\":[" +
                "{\"product\":\"RTIX\",\"quantity\":25,\"price\":\"$0.67\",\"type\":\"3XL\",\"industry\":\"Industrial Specialties\",\"origin\":\"LA\"},\n" +
                "{\"product\":\"UTX\",\"quantity\":82,\"price\":\"$4.84\",\"type\":\"S\",\"industry\":\"Aerospace\",\"origin\":\"TX\"}]}";

        assertNotNull(result);
        assertEquals("data_test.json", result.getFilename());
        assertEquals(expectedJson, result.getJson());

        verify(fileImportRepository, times(1)).findByName("data_test.json");
    }

    @Test
    void givenAlreadyImported_whenRead_thenShouldNotLoadResourceAndFileName() throws Exception {
        Resource givenResource = new ClassPathResource("fixture/data_test.json");
        Resource[] givenResources = singletonList(givenResource).toArray(new Resource[0]);

        stockItemReader = new StockItemReader(givenResources, fileImportRepository);

        when(fileImportRepository.findByName("data_test.json")).thenReturn(Optional.of(FileImport.builder().id(1L).name("data_test.json").build()));

        final ItemRead result = stockItemReader.read();

        String expectedJson = "{\"data\":[" +
                "{\"product\":\"RTIX\",\"quantity\":25,\"price\":\"$0.67\",\"type\":\"3XL\",\"industry\":\"Industrial Specialties\",\"origin\":\"LA\"},\n" +
                "{\"product\":\"UTX\",\"quantity\":82,\"price\":\"$4.84\",\"type\":\"S\",\"industry\":\"Aerospace\",\"origin\":\"TX\"}]}";

        assertNull(result);

        verify(fileImportRepository, times(1)).findByName("data_test.json");
    }
}