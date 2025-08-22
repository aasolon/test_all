package com.example.testall.fintech;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class FintechPaymentController {

    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json", "application/jose"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public FintechPayment create(
            @RequestBody
            @Valid
            FintechPayment paymentDto
    ) {
        return new FintechPayment();
    }
}
