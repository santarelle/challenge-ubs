package com.ubs.batch.domain.job;

import com.ubs.batch.domain.entity.Stock;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Builder
public class ItemProcessed implements Serializable {

    private String filename;
    private Set<Stock> stocks;
}
