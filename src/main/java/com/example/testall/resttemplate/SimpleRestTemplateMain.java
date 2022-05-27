package com.example.testall.resttemplate;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SimpleRestTemplateMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
//            String forObject = restTemplate.getForObject("http://localhost:9876", String.class);
            ResponseEntity<Void> asd = restTemplate.exchange("http://localhost:9876/with-param", HttpMethod.GET, new HttpEntity<>(null), Void.class);
            int i = 0;
        } catch (Exception e) {
            int i = 0;
        }
    }
}
