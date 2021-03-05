package com.example.testall.sender;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SenderReceiverController {

    @RequestMapping(value = "/test-sender-json", consumes = "application/json")
    public UserResult receiveJson(User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_JSON");
        return userResult;
    }

    @RequestMapping(value = "/test-sender-xml", consumes = "application/xml")
    public UserResult receiveXml(User user) {
        UserResult userResult = new UserResult();
        userResult.setResult("RECEIVED_XML");
        return userResult;
    }
}
