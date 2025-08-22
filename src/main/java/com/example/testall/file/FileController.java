package com.example.testall.file;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
