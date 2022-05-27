package com.example.testall.ddosclient;

import com.example.testall.simplecontroller.SimpleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

//@Component
public class MultiThreadDdoser {

    private static final Logger log = LoggerFactory.getLogger(MultiThreadDdoser.class);

    private static final int GATEWAY_THREAD_COUNT = 190;
    private static final int PROMETHEUS_THREAD_COUNT = 1;
    private static final int LIVENESS_THREAD_COUNT = 1;
    private static final int READINESS_THREAD_COUNT = 1;

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private TaskScheduler ddosTaskScheduler;

    @PostConstruct
    public void startDdosThreads() {
        for (int i = 0; i < GATEWAY_THREAD_COUNT; i++) {
            ddosTaskScheduler.scheduleAtFixedRate(
                    () -> {
                        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
                                .method(HttpMethod.POST, "http://localhost:8081/digitalapi/gateway")
//                                .method(HttpMethod.POST, "http://localhost:8081/digitalapi/external-request")
                                .contentType(MediaType.APPLICATION_JSON);

                        String body = getGatewayBody();
//                        String body = getExtReqBody();
                        RequestEntity<?> requestEntity = requestEntityBuilder.body(body);

                        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
                        log.info("received response for TESTING REST " + exchange.getStatusCodeValue());
                    },
                    Instant.now().plusSeconds(5),
                    Duration.ofSeconds(1)
            );
        }







        for (int i = 0; i < PROMETHEUS_THREAD_COUNT; i++) {
            ddosTaskScheduler.scheduleAtFixedRate(
                    () -> {
                        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
                                .method(HttpMethod.GET, "http://localhost:8081/actuator/prometheus");
                        RequestEntity<?> requestEntity = requestEntityBuilder.build();

                        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
                        log.info("received response for PROMETHEUS " + exchange.getStatusCodeValue());
                    },
                    Instant.now().plusSeconds(5),
                    Duration.ofSeconds(1)
            );
        }






        for (int i = 0; i < LIVENESS_THREAD_COUNT; i++) {
            ddosTaskScheduler.scheduleAtFixedRate(
                    () -> {
                        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
                                .method(HttpMethod.GET, "http://localhost:8081/actuator/health/liveness");
                        RequestEntity<?> requestEntity = requestEntityBuilder.build();

                        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
                        log.info("received response for LIVENESS " + exchange.getStatusCodeValue());
                    },
                    Instant.now().plusSeconds(5),
                    Duration.ofSeconds(1)
            );
        }




        for (int i = 0; i < READINESS_THREAD_COUNT; i++) {
            ddosTaskScheduler.scheduleAtFixedRate(
                    () -> {
                        RequestEntity.BodyBuilder requestEntityBuilder = RequestEntity
                                .method(HttpMethod.GET, "http://localhost:8081/actuator/health/readiness");
                        RequestEntity<?> requestEntity = requestEntityBuilder.build();

                        ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);
                        log.info("received response for READINESS " + exchange.getStatusCodeValue());
                    },
                    Instant.now().plusSeconds(5),
                    Duration.ofSeconds(1)
            );
        }
    }

    private String getGatewayBody() {
        UUID uuid = UUID.randomUUID();
        return  "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"checkRequestAllowed\",\n" +
                "  \"id\": 1,\n" +
                "  \"params\": {\n" +
                "    \"accessToken\": \"" + uuid + "\",\n" +
                "    \"endpointRequest\": {\n" +
                "      \"urlPath\": \"/fintech/api/v1/payroll\",\n" +
                "      \"method\": \"POST\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }

    private String getExtReqBody() {
        UUID uuid = UUID.randomUUID();
        return "{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"method\": \"save\",\n" +
                "  \"id\": 1,\n" +
                "  \"params\": {\n" +
                "    \"externalRequestInfo\": {\n" +
                "      \"requestId\": \"" + uuid + "\",\n" +
                "      \"urlPath\": \"/v1/payments/9d18ebcb-c50a-4f75-9157-2b34b9e8c61d/state\",\n" +
                "      \"clientId\": \"1005\",\n" +
                "      \"digitalId\": \"6847616714882875393\",\n" +
                "      \"digitalUserId\": \"40802810600000200000\",\n" +
                "      \"exceptionType\": \"ACCESS_ERROR\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
    }
}
