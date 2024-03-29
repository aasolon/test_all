package com.example.testall.file;

import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadBigFile {

    public static void main(String[] args) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.execute(
                "http://localhost:8082/download-file-proxy",
                HttpMethod.GET,
                null,
                clientHttpResponse -> {
                    File file = File.createTempFile("download", "tmp");
                    StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(file, true));
                    return file;
                });
    }
}
