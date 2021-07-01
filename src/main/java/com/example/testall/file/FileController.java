package com.example.testall.file;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<FileSystemResource> stream() {
        return ResponseEntity.ok()
                .header("X-File-Custom-Header-1111", "2222222222222222222")
                .body(new FileSystemResource("VID_20190524_215856.mp4"));
    }
}
