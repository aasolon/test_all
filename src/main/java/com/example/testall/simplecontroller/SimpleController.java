package com.example.testall.simplecontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/hello")
    public String hello() {
        return "Heeello hi!";
    }

    // https://localhost:10012/ott-service/TokenJsonRpcFacade.json
    @PostMapping("/ott-service/TokenJsonRpcFacade.json")
    public String ott(Object obj) {
        return "{\"jsonrpc\": \"2.0\", \"result\": 19, \"id\": 3}";
    }
}
