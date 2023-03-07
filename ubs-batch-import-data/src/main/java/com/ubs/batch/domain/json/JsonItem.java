package com.ubs.batch.domain.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ubs.batch.util.deserializer.PriceDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonItem implements Serializable {

    private String product;
    private Integer quantity;
    @JsonDeserialize(using = PriceDeserializer.class, contentAs = String.class)
    private BigDecimal price;
    private String type;
    private String industry;
    private String origin;

}
