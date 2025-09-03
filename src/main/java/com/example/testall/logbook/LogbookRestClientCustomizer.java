package com.example.testall.logbook;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@Component
public class LogbookRestClientCustomizer implements RestClientCustomizer {

    private final Logbook httpClientLogbook;

    public LogbookRestClientCustomizer(@Qualifier("httpClientLogbook") Logbook httpClientLogbook) {
        this.httpClientLogbook = httpClientLogbook;
    }

    @Override
    public void customize(RestClient.Builder restClientBuilder) {
        restClientBuilder.requestInterceptor(new LogbookClientHttpRequestInterceptor(httpClientLogbook));
    }
}
