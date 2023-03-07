package com.ubs.batch.domain.job;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ItemRead implements Serializable {

    private String filename;
    private String json;
}
