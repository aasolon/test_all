package com.example.testall.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.beans.ConstructorProperties;

public class NoAnnotationDeserializer {

    public static void main(String... args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParameterNamesModule());
        System.out.println(
                mapper.readValue(
                        "{\"a\": \"aaa\", \"b\": \"bbb\", \"c\": \"ccc\"}",
                        Govno.class
                )
        );
    }

    static class Govno {
        private String a;
        private String b;
        private String c;

        @JsonCreator
        public Govno(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }
}
