package com.example.testall.filerest;

import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileMain {

    public static void main(String[] args) throws IOException {
        File file = File.createTempFile("download", "tmp");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.execute(
                "http://localhost:8081/download-file",
                HttpMethod.GET,
                clientHttpRequest -> {
                    clientHttpRequest.getHeaders().set(
                            "Range",
                            String.format("bytes=%d-%d", 0, 1));
                },
                clientHttpResponse -> {
                    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(file, true));
                    return file;
                });

        restTemplate.execute(
                "http://localhost:8081/download-file",
                HttpMethod.GET,
                clientHttpRequest -> {
                    clientHttpRequest.getHeaders().set(
                            "Range",
                            String.format("bytes=%d-%d", file.length(), 3));
                },
                clientHttpResponse -> {
                    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(file, true));
                    return file;
                });
    }
}
