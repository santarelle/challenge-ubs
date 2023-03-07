package com.ubs.batch.job.reader;

import com.ubs.batch.domain.job.ItemRead;
import com.ubs.batch.repository.FileImportRepository;
import com.ubs.batch.util.resource.ResourceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StockItemReader implements ItemReader<ItemRead> {

    private final Resource[] inputFiles;
    private final FileImportRepository fileImportRepository;

    public StockItemReader(@Value("classpath*:/data/data_*.json") Resource[] inputFiles,
                           FileImportRepository fileImportRepository) {
        this.inputFiles = inputFiles;
        this.fileImportRepository = fileImportRepository;
    }

    private ItemReader<ItemRead> itemReader;

    @Override
    public ItemRead read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        log.debug("read()");
        if (itemReader == null) {
            itemReader = new IteratorItemReader<>(items());
        }
        return itemReader.read();
    }

    private List<ItemRead> items() {
        return Arrays.stream(inputFiles)
                .parallel()
                .filter(filterNonImportedFile())
                .map(resource -> ItemRead.builder()
                        .filename(resource.getFilename())
                        .json(ResourceUtil.content(resource))
                        .build())
                .collect(Collectors.toList());
    }

    private Predicate<Resource> filterNonImportedFile() {
        return resource -> {
            final boolean nonImported = !fileImportRepository.findByName(resource.getFilename()).isPresent();
            if (!nonImported) {
                log.info("File {} already imported to database", resource.getFilename());
            }
            return nonImported;
        };
    }
}
