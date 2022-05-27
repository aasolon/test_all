package com.example.testall.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        sipmpleDeserialize();

        Wrapper wrapper = new Wrapper();
        Map<String, SimpleModel> map = new HashMap<>();
        map.put("111", new SimpleModel(1, "a"));
        map.put("222", new SimpleModel(2, "b"));
        map.put("333", new SimpleModel(3, "c"));
        wrapper.setMap(map);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wrapper));
    }

    private static void sipmpleDeserialize() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModel simpleModel = objectMapper.readValue("{\"count\":\"123\",\"name\":\"a\"}", SimpleModel.class);
        int i = 0;
    }

    private static class Wrapper {
        private Map<String, SimpleModel> map;

        public Map<String, SimpleModel> getMap() {
            return map;
        }

        public void setMap(Map<String, SimpleModel> map) {
            this.map = map;
        }
    }

    private static class SimpleModel {
        private int count;
        private String name;

        public SimpleModel() {}

        public SimpleModel(int count, String name) {
            this.count = count;
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
