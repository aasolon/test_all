package com.example.testall.simplecontroller;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);

    CloseableHttpClient client = HttpClientBuilder
            .create()
            .setDefaultRequestConfig(
                    RequestConfig.custom()
                            .setConnectTimeout(60 * 1000)
                            .setConnectionRequestTimeout(5 * 1000)
                            .setSocketTimeout(60 * 1000)
                            .build()
            )
            .setMaxConnPerRoute(2)
            .setMaxConnTotal(2)
            .build();

    @GetMapping("/hello")
    public String hello() {
        return "Heeello hi! 111111111111";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "Heeello hi! 2222222222";
    }

    @GetMapping("/hello3")
    public String hello3() {
        return "Heeello hi! 33333333333";
    }

    // https://localhost:10012/ott-service/TokenJsonRpcFacade.json
    @PostMapping("/ott-service/TokenJsonRpcFacade.json")
    public String ott(Object obj) {
        return "{\"jsonrpc\": \"2.0\", \"result\": 19, \"id\": 3}";
    }

    @GetMapping("/hello_2")
    public String hello_2() throws IOException {
        log.info(" /hello_2 received request");

        HttpGet get = new HttpGet("http://localhost:8081/hello_with_sleep");
        HttpResponse response = client.execute(get);

        return IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
    }

    @GetMapping("/hello_with_sleep")
    public String helloWithSleep() throws InterruptedException {
        log.info(" /hello_with_sleep received request");
        Thread.sleep(30 * 1000);
        log.info(" /hello_with_sleep send response");
        return "Heeello hi!";
    }
}
