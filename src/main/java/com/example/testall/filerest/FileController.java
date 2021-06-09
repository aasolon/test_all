package com.example.testall.filerest;

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

    @GetMapping(value = "/download-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> stream() {
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.add("Content-Type", "video/mp4");
//        return new ResponseEntity<>(new FileSystemResource(filePathString), responseHeaders, HttpStatus.OK);
        return ResponseEntity.ok().body(new FileSystemResource("test_file.txt"));
    }
}
