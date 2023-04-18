package com.example.testall.resttemplate;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class SimpleRestTemplateMain {

    public static void main(String[] args) {
        try {
//            callPlainRestTemplate();
            callRestTemplateWithApacheHttpClient();
        } catch (Exception e) {
            int i = 0;
        }
    }

    private void callPlainRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//            String forObject = restTemplate.getForObject("http://localhost:9876", String.class);
        ResponseEntity<Void> asd = restTemplate.exchange("http://localhost:9876/with-param", HttpMethod.GET, new HttpEntity<>(null), Void.class);
        int i = 0;
    }

    private static void callRestTemplateWithApacheHttpClient() {
//        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
//        connectionManager.setMaxTotal(100);
//        connectionManager.setDefaultMaxPerRoute(20);

        HttpClient httpClient = HttpClientBuilder.create()
//                .setConnectionManager(connectionManager)
                .setDefaultRequestConfig(
                        RequestConfig.custom()
                                .setConnectTimeout(60 * 1000)
                                .setConnectionRequestTimeout(30 * 1000)
                                .setSocketTimeout(60 * 1000)
                                .build()
                )
                .setMaxConnPerRoute(2)
                .setMaxConnTotal(5)
                .build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);


//        RestTemplate restTemplate1 = new RestTemplate(requestFactory);
//        String response1 = restTemplate1.getForObject("http://httpbin.org/get", String.class);
//
//        restTemplate1 = new RestTemplate(requestFactory);
//        response1 = restTemplate1.getForObject("http://httpbin.org/get", String.class);
//
//        restTemplate1 = new RestTemplate(requestFactory);
//        response1 = restTemplate1.getForObject("http://httpbin.org/get", String.class);
//
//        RestTemplate restTemplate2 = new RestTemplate(requestFactory);
//        String response2 = restTemplate2.getForObject("http://ya.ru", String.class);

        RestTemplate restTemplate3 = new RestTemplate(requestFactory);
        String response3 = restTemplate3.getForObject("http://www.google.com", String.class);
        int i = 0;
    }
}
