package com.example.testall.sender;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderReceiverController {

    @RequestMapping(value = "/test-sender-json", consumes = "application/json")
    public UserResult receiveJson(@RequestBody User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_JSON");
        userResult.setTimestamp(System.currentTimeMillis());
        return userResult;
    }

    @RequestMapping(value = "/test-sender-xml", consumes = "application/xml")
    public UserResult receiveXml(@RequestBody User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_XML");
        userResult.setTimestamp(System.currentTimeMillis());
        return userResult;
    }
}
