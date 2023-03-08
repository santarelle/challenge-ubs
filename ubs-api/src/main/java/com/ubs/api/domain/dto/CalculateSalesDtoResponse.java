package com.ubs.api.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class CalculateSalesDtoResponse implements Serializable {

    private String store;
    private List<CalculateSalesDetail> details;
    private CalculateSalesSummary summary;

}
