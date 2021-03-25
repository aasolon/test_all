package com.example.testall.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JacksonTimeZone {

    public static void main(String[] args) throws ParseException, JsonProcessingException {
        String dateStr = "25.12.2019";
        Date date = new SimpleDateFormat("dd.MM.yyyy").parse(dateStr);
        JsonObject jsonObject = new JsonObject();
        jsonObject.setDate(new Date());
        jsonObject.setDateTime(new Date());

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
//        objectMapper.setTimeZone(TimeZone.getDefault()); // каментим эту строку и результат другой
        System.out.println(objectMapper.writeValueAsString(jsonObject));

        String jsonStr = "{\n" +
                "  \"date\" : \"2021-03-25\",\n" +
                "  \"dateTime\" : \"2021-03-25T01:51:13\"\n" +
                "}";
        JsonObject jsonObject1 = objectMapper.readValue(jsonStr, JsonObject.class);
        System.out.println("Deserialized date: " + jsonObject1.getDate());
        System.out.println("Deserialized date time: " + jsonObject1.getDateTime());

        Date localTime = new Date();
        System.out.println("Local Time: " + localTime);

        String format = "yyyy/MM/dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String utcTime = sdf.format(localTime);

        System.out.println("UTC Time: " + utcTime);
    }

    private static class JsonObject {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private Date date;

//        @JsonDeserialize(using = DateTimeJsonDeserializer.class) // каментим эту строку и результат другой
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:SS")
        private Date dateTime;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getDateTime() {
            return dateTime;
        }

        public void setDateTime(Date dateTime) {
            this.dateTime = dateTime;
        }
    }
}
