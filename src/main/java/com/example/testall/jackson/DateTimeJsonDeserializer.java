package com.example.testall.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.time.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * Десериализатор даты со временем
 */
public class DateTimeJsonDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        String text = parser.getCodec().<JsonNode>readTree(parser).asText();
        try {
            return DateUtils.parseDateStrictly(text, "yyyy-MM-dd'T'HH:mm:ss");
        } catch (ParseException e) {
            throw context.weirdStringException(text, Date.class, "not a valid representation");
        }
    }
}
