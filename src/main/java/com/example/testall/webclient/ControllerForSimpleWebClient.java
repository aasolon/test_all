package com.example.testall.webclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerForSimpleWebClient {

    @GetMapping("/badrequest-for-webclient")
    public ResponseEntity badRequest() {
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/servererror-for-webclient")
    public ResponseEntity serverError() {
        return ResponseEntity.status(500).build();
    }
}
