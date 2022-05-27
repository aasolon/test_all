package com.example.testall.file;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileController {

/*
curl -I localhost:8081/download-file

HTTP/1.1 200
Accept-Ranges: bytes
Content-Type: text/plain
Content-Length: 34
Date: Tue, 08 Jun 2021 15:22:20 GMT
*/

    @GetMapping(value = "/download-file", produces= "video/mp4")
    public ResponseEntity<FileSystemResource> downloadFile() {
        return ResponseEntity.ok()
                .header("X-File-Custom-Header-1111", "2222222222222222222")
                .body(new FileSystemResource("VID_20190524_215856.mp4"));
    }

    @GetMapping(value = "/download-file-proxy", produces= "video/mp4")
    public ResponseEntity<InputStreamResource> downloadFileProxy() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8082/download-file");
        CloseableHttpResponse response1 = client.execute(httpGet);
//        try (CloseableHttpResponse response1 = client.execute(httpGet)) {
            HttpEntity entity = response1.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
//                try (InputStream inputStream = entity.getContent()) {
                    return ResponseEntity.ok()
                            .header("X-File-Custom-Header-1111", "2222222222222222222")
                            .body(new InputStreamResource(inputStream));
//                }
            }
//        }
        return ResponseEntity.ok()
                .header("X-File-Custom-Header-1111", "2222222222222222222")
                .body(null);
    }
}
