package com.example.testall.fintech;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Сериализатор для полей с суммами
 */
public class FintechMoneyJsonSerializer extends JsonSerializer<BigDecimal> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString());
    }
}
