package com.example.testall.webclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@RestController
public class WebClientController {

    @Autowired
    private AsyncRestTemplateService asyncRestTemplateService;

    @GetMapping("/get-result-and-audit")
    public String getResultAndAudit() {
        return usingWebClient();
//        return usingAsync();
//        return usingCompletableFuture();
    }

    @GetMapping("/long-audit")
    public String longAudit() throws InterruptedException {
        Thread.sleep(5000);
        System.out.println(Thread.currentThread() + "AUDIT COMPLETE");
        return "AUDIT COMPLETE";
    }

    private String usingWebClient() {
        System.out.println(Thread.currentThread() + " start /get-result-and-audit");
        String result = "RESULT_IS_READY";

        // audit
        Mono<String> voidMono = WebClient.create("http://localhost:8081/long-audit").get()
                .retrieve()
                .bodyToMono(String.class)
                .doOnRequest(e -> {
                    System.out.println(Thread.currentThread() + " doOnRequest: " + e);
                })
                .doOnSubscribe(e -> {
                    System.out.println(Thread.currentThread() + " doOnSubscribe: " + e);
                })
                .doOnSuccess(e -> {
                    System.out.println(Thread.currentThread() + " doOnSuccess: " + e);
                })
                .doOnNext(e -> {
                    System.out.println(Thread.currentThread() + " doOnNext: " + e);
                });

        voidMono.subscribe(e -> {
            System.out.println(Thread.currentThread() + " subscribe: " + e);
        });

//        voidMono.delaySubscription(Duration.ofSeconds(5)).subscribe(e -> {
//            System.out.println(Thread.currentThread() + " subscribe2: " + e);
//        });

        return result;
    }


    private String usingAsync() {
        String result = "RESULT_IS_READY";
        asyncRestTemplateService.usingAsync();
        return result;
    }

    private String usingCompletableFuture() {
        String result = "RESULT_IS_READY";
        CompletableFuture.runAsync(() -> {
            RestTemplate restTemplate = new RestTemplate();
            String auditResult = restTemplate.getForObject("http://localhost:8081/long-audit", String.class);
            System.out.println(Thread.currentThread() + " AUDIT SUCCESSFULLY SENT by CompletableFuture: " + auditResult);
        });
        return result;
    }
}
