package com.ubs.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
public class CalculateSalesDetail implements Serializable {

    private Integer quantity;
    private BigDecimal price;
    private BigDecimal volume;

}
