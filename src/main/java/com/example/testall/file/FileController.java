package com.example.testall.file;

import org.springframework.core.io.FileSystemResource;
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

    @GetMapping(value = "/download-file")
    public ResponseEntity<FileSystemResource> stream() {
        return ResponseEntity.ok()
                .header("X-File-AAAAAAA", "111111111111111")
                .body(new FileSystemResource("Ace_Stream_Media_3.1.32.exe"));
    }
}
