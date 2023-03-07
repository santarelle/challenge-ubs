package com.ubs.batch.domain.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonData implements Serializable {

    @JsonProperty("data")
    private List<JsonItem> data;
}
