package com.example.testall.jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

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
        objectMapper.setTimeZone(TimeZone.getDefault()); // каментим эту строку и результат другой
        System.out.println(objectMapper.writeValueAsString(jsonObject));

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
