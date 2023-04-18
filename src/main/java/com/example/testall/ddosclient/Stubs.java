package com.example.testall.ddosclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class Stubs {

    private static final Logger log = LoggerFactory.getLogger(Stubs.class);

    @GetMapping(value = "/v2/check-access-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessTokenInfo(@RequestParam String accessToken, @RequestHeader HttpHeaders headers) {
        log.info("/v2/check-access-token ===========================================================================================\n" +
                "Headers:\n" +
                getHeadersAsString(headers));

        return "{\n" +
                "  \"accessToken\": \"" + accessToken + "\",\n" +
                "  \"claims\": [\n" +
                "    \"sid2\",\n" +
                "    \"sub\",\n" +
                "    \"iss\",\n" +
                "    \"aud\",\n" +
                "    \"exp\",\n" +
                "    \"iat\",\n" +
                "    \"auth_time\",\n" +
                "    \"acr\",\n" +
                "    \"amr\",\n" +
                "    \"azp\",\n" +
                "    \"nonce\"\n" +
                "  ],\n" +
                "  \"services\": [\n" +
                "    \"PAYROLL\"\n" +
                "  ],\n" +
                "  \"userEpkId\": 1446349517043562433,\n" +
                "  \"clientEpkId\": 1446349517043562433,\n" +
                "  \"digitalUserId\": \"6966888591978594305\",\n" +
                "  \"digitalId\": \"6877890546340134913\",\n" +
                "  \"clientId\": 111251,\n" +
                "  \"userLogin\": \"dkov_delmir\"\n" +
                "}";
    }

    @GetMapping(value = "/digitalapi/fpendpoints", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getParams() {
        return "{\n" +
                "  \"endpointData\": [\n" +
                "    {\n" +
                "      \"urlPath\": \"/fintech/api/v1/payroll\",\n" +
                "      \"method\": \"POST\",\n" +
                "      \"serviceCodes\": [\"PAYROLL\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    private String getHeadersAsString(HttpHeaders headers) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            builder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }
}
