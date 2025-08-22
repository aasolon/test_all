package com.example.testall.resttemplate;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;

/**
 * by default the RestTemplate relies on standard JDK facilities to establish HTTP connections. You can switch to use a different HTTP library such as Apache HttpComponents
 */
public class RestTemplateProxyMain {

    public static void main(String[] args) {
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

//        restTemplateWithSpringProxy(loggingInterceptor);

//        restTemplateWithHttpClientProxy(loggingInterceptor);

        restTemplateWithoutProxy(loggingInterceptor);

        int i = 0;
    }

    // Accept: text/plain, application/json, application/*+json, */*
    // Host: httpbin.org
    // Proxy-Connection: keep-alive
    // User-Agent: Java/11.0.10
    private static void restTemplateWithSpringProxy(LoggingInterceptor loggingInterceptor) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8082));
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setProxy(proxy);

        RestTemplate restTemplateWithProxy = new RestTemplate(requestFactory);
        restTemplateWithProxy.setInterceptors(Collections.singletonList(loggingInterceptor));
        ResponseEntity<String> responseEntity = restTemplateWithProxy.getForEntity("http://httpbin.org/get", String.class);
        System.out.println(responseEntity.getBody());
    }

    // Opening connection {}->http://localhost:8082->http://httpbin.org:80
    // Connecting to localhost/127.0.0.1:8082
    // Connection established 127.0.0.1:49816<->127.0.0.1:8082

    // GET http://httpbin.org/get HTTP/1.1
    // Accept: text/plain, application/json, application/*+json, */*
    // Host: httpbin.org
    // Proxy-Connection: Keep-Alive
    // User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.10)
    // Accept-Encoding: gzip,deflate

    //{
    //  "args": {},
    //  "headers": {
    //    "Accept": "text/plain, application/json, application/*+json, */*",
    //    "Accept-Encoding": "gzip,deflate",
    //    "Content-Length": "0",
    //    "Hello": "World",
    //    "Host": "httpbin.org",
    //    "Proxy-Connection": "Keep-Alive",
    //    "User-Agent": "Apache-HttpClient/4.5.12 (Java/11.0.10)",
    //    "X-Amzn-Trace-Id": "Root=1-60fa6e9b-340fc7e553ea41041bcbf3cc"
    //  },
    //  "origin": "194.186.207.140",
    //  "url": "http://httpbin.org/get"
    //}
    private static void restTemplateWithHttpClientProxy(LoggingInterceptor loggingInterceptor) {
        HttpHost proxy = new HttpHost("localhost", 8082);
        HttpClient httpClient = HttpClients.custom().setProxy(proxy).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplateWithProxy = new RestTemplate(requestFactory);
        ResponseEntity<String> responseEntity = restTemplateWithProxy.getForEntity("http://httpbin.org/get", String.class);
        System.out.println(responseEntity.getBody());
    }

    // Opening connection {}->http://localhost:8082
    // Connecting to localhost/127.0.0.1:8082
    // Connection established 127.0.0.1:49775<->127.0.0.1:8082

    // GET /get HTTP/1.1
    // Accept: text/plain, application/json, application/*+json, */*
    // Host: localhost:8082
    // Connection: Keep-Alive
    // User-Agent: Apache-HttpClient/4.5.12 (Java/11.0.10)
    // Accept-Encoding: gzip,deflate

    //{
    //  "args": {},
    //  "headers": {
    //    "Accept": "text/plain, application/json, application/*+json, */*",
    //    "Accept-Encoding": "gzip,deflate",
    //    "Content-Length": "0",
    //    "Hello": "World",
    //    "Host": "httpbin.org",
    //    "User-Agent": "Apache-HttpClient/4.5.12 (Java/11.0.10)",
    //    "X-Amzn-Trace-Id": "Root=1-60fa6dfd-7fb8caec3f8773b96b839428"
    //  },
    //  "origin": "194.186.207.140",
    //  "url": "http://httpbin.org/get"
    //}
    private static void restTemplateWithoutProxy(LoggingInterceptor loggingInterceptor) {
        RestTemplate restTemplate2 = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        ResponseEntity<String> responseEntity2 = restTemplate2.getForEntity("http://localhost:8082/get", String.class);
        System.out.println(responseEntity2.getBody());
    }
}
