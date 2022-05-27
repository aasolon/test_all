package com.example.testall.webclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

public class SimpleWebClientMain {

    public static final String BAD_REQUEST_URL = "/badrequest-for-webclient";
    public static final String SERVER_ERROR_URL = "/servererror-for-webclient";

    public static void main(String[] args) throws InterruptedException {
//        webClientExchange();

//        restTemplateAnalogue();

        int i = 0;
    }

    private static void webClientExchange() throws InterruptedException {
        WebClient webClient = WebClient.create("http://localhost:8081");
        webClient
                .get()
                .uri(BAD_REQUEST_URL)
                .exchange()
                .flatMap(e -> {
                    if (e.statusCode().is5xxServerError())
                        return Mono.just(false);
                    if (e.statusCode() == HttpStatus.OK)
                        return Mono.just(true);
                    return Mono.error(new RuntimeException("BAD RESPONSE"));
                })
                .onErrorResume(e -> Mono.error(new RuntimeException("UNEXPECTED ERROR", e)))
                .doOnSuccess(e -> System.out.println("============================ Result " + e))
                .subscribe();

        Thread.sleep(5000);
        int i = 0;
    }

    private static void restTemplateAnalogue() {
        ResponseEntity<String> responseEntity;
        HttpEntity<String> requestEntity = new HttpEntity<>("", new HttpHeaders());
        RestTemplate restTemplate = new RestTemplate();
        try {
            responseEntity = restTemplate.exchange("http://localhost:8085" + BAD_REQUEST_URL, HttpMethod.GET, requestEntity, String.class);
        } catch (HttpServerErrorException e) {
            int i = 0;
        }
        int i = 0;
    }
}
