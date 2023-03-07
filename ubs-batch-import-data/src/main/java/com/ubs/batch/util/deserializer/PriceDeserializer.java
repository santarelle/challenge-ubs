package com.ubs.batch.util.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class PriceDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        final String valueAsString = jsonParser.readValueAs(String.class);
        return valueAsString.startsWith("$")
                ? new BigDecimal(valueAsString.substring(1))
                : new BigDecimal(valueAsString);
    }
}
