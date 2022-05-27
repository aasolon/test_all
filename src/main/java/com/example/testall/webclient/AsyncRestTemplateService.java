package com.example.testall.webclient;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AsyncRestTemplateService {

    @Async
    public void usingAsync() {
        RestTemplate restTemplate = new RestTemplate();
        String auditResult = restTemplate.getForObject("http://localhost:8081/long-audit", String.class);
        System.out.println(Thread.currentThread() + " AUDIT SUCCESSFULLY SENT by Async Service: " + auditResult);
    }
}
