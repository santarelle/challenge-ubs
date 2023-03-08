package com.ubs.api.domain.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class CalculateSalesDtoRequest implements Serializable {

    @NotNull
    private String product;

    @NotNull
    private Integer storeQuantity;

}
