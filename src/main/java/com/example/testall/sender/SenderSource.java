package com.example.testall.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SenderSource {

    public static void main(String[] args) throws JsonProcessingException {
        // 1. Сформируем тело запроса, которое необходимо отправить во внешнюю АС
        User user = new User();
        user.setName("Alice");
        user.setCount(999);
        // 2. Конвертнем тело запроса для внешней АС в строку, чтобы потом подложить тело в HttpSenderRequest
        ObjectMapper objectMapper = new ObjectMapper();
        String userAsString = objectMapper.writeValueAsString(user);

        // 3. Сформируем объект HttpSenderRequest с инф-ей о запросе, объект httpSenderRequest будет передан сендеру
        HttpSenderRequest httpSenderRequest = new HttpSenderRequest();
        httpSenderRequest.setId(UUID.randomUUID());
        httpSenderRequest.setMethod("POST");
        httpSenderRequest.setUrl("http://localhost:8081/test-sender-json");
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/json");
        httpSenderRequest.setHeaders(headersMap);
        httpSenderRequest.setBody(userAsString);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<HttpSenderRequest> httpEntity = new HttpEntity<>(httpSenderRequest, headers);

        // 4. Отправляем запрос на сендера, сендер выцепит инф-ию о запросе из HttpSenderRequest, сформирует окончательный HTTP-запрос и отправит его во внешнюю АС, т.е. в SenderReceiverController
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8080/fintech-webhook-sender/rest/send-to-external-system", httpEntity, String.class);
        int i = 0;
    }

    private static class HttpSenderRequest {
        /**
         * Уникальный идентификатор запроса
         */
        private UUID id;

        /**
         * Http метод
         */
        private String method;

        /**
         * Заголовки HTTP-запроса
         */
        private Map<String, String> headers = new HashMap<>();

        /**
         * URL, на который необходимо отправить HTTP-запрос
         */
        private String url;

        /**
         * Параметры HTTP-запроса, которые будут добавлены в URL
         */
        private Map<String,String> queryParams = new HashMap<>();

        /**
         * Тело HTTP-запроса
         */
        private String body;


        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getQueryParams() {
            return queryParams;
        }

        public void setQueryParams(Map<String, String> queryParams) {
            this.queryParams = queryParams;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
