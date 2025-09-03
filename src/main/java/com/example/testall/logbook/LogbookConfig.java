package com.example.testall.logbook;

import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zalando.logbook.HttpRequest;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.BodyFilters;
import org.zalando.logbook.core.Conditions;
import org.zalando.logbook.core.HeaderFilters;

import java.util.function.Predicate;

@Configuration
public class LogbookConfig {

    @Autowired
    private ServletContext servletContext;

    @Bean
    @Primary
    public Logbook defaultLogbook(WebEndpointProperties webEndpointProperties) {
        boolean enabled = true;
        int maxLength = 100;
        return Logbook.builder()
                .condition(((Predicate<HttpRequest>) (req) -> enabled)
                        .and(Conditions.exclude(Conditions.requestTo(servletContext.getContextPath() + webEndpointProperties.getBasePath() + "/**"))))
                .headerFilter(HeaderFilters.removeHeaders("x-forwarded-client-cert"))
                .bodyFilter(maxLength >= 0 ? BodyFilters.truncate(maxLength) : (contentType, body) -> body)
                .build();
    }

    @Bean
    public Logbook httpClientLogbook() {
        boolean enabled = true;
        int maxLength = 10;
        return Logbook.builder()
                .condition(req -> enabled)
                .bodyFilter(maxLength >= 0 ? BodyFilters.truncate(maxLength) : (contentType, body) -> body)
                .build();
    }
}
