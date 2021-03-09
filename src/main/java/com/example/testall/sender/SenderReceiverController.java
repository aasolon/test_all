package com.example.testall.sender;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderReceiverController {

    @PostMapping(value = "/test-sender-json", consumes = "application/json")
    public UserResult receiveJson(@RequestBody User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_JSON");
        userResult.setTimestamp(System.currentTimeMillis());
        return userResult;
    }

    @PostMapping(value = "/test-sender-xml", consumes = "application/xml")
    public UserResult receiveXml(@RequestBody User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_XML");
        userResult.setTimestamp(System.currentTimeMillis());
        return userResult;
    }
}
