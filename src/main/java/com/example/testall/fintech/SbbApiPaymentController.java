package com.example.testall.fintech;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.sberbank.pprb.sbbol.payments.server.api.PaymentsApi;
import ru.sberbank.pprb.sbbol.payments.server.model.FintechPayment;
import ru.sberbank.pprb.sbbol.payments.server.model.FintechPaymentIncoming;

@Controller
public class SbbApiPaymentController implements PaymentsApi {

    @Override
    public ResponseEntity<FintechPayment> createPayment(FintechPaymentIncoming fintechPaymentIncoming) {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);;
        FintechPayment fintechPayment = objectMapper.convertValue(fintechPaymentIncoming, FintechPayment.class);
        fintechPayment.setNumber("123321");
        return ResponseEntity.ok(fintechPayment);
    }
}
