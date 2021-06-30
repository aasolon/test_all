package com.example.testall.fintech;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FintechWebhookSenderController {

    @GetMapping("/fintech-webhook-sender/rest/send-to-external-system")
    public String sendToExternalSystem() {
        if (true)
            throw new RuntimeException("GGGG");
        return "FINTECH WEBHOOK SENDER ___RESPONSE";
    }
}
