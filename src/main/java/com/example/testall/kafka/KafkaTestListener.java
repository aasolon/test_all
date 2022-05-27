package com.example.testall.kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Service
public class KafkaTestListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaTestListener.class);

    @KafkaListener(topics = "request-topic", containerFactory = "testAllKafkaListenerContainerFactory")
    public void listenTestAllKafkaTopic(@Payload String message,
                                        @Header(KafkaHeaders.GROUP_ID) String groupId,

                                        @Header("stringHeader") String stringHeader,
                                        @Header("longHeader") long longHeader,
                                        @Header("uuidHeader") UUID uuidHeader,
                                        @Header("byteHeader") byte[] byteHeader,
                                        @Header("objectHeader") byte[] objectHeader,
                                        @Header("objectArrayHeader") byte[] objectArrayHeader,

                                        @Headers Map<String, Object> headersMap,
                                        @Headers MessageHeaders messageHeaders) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        KafkaObjectHeader2 kafkaObjectHeader2 = objectMapper.readValue(objectHeader, KafkaObjectHeader2.class);

        log.info("\nReceived Message from Kafka\n" +
                "\nSingle headers:\n" +
                "groupId      = " + groupId + "\n" +

                "stringHeader = " + stringHeader + "\n" +
                "longHeader   = " + longHeader + "\n" +
                "uuidHeader   = " + uuidHeader + "\n" +
                "byteHeader   = " + IOUtils.toString(byteHeader) + "\n" +
                "objectHeader   = " + objectHeader + "\n" +

                "\nAll headers:\n" + headersMap + "\n" +
                "\nMessage:\n" + message + "\n");

        if (true) {
            throw new KafkaListenerCustomException("Oops :(");
        }
    }

//    @KafkaListener(topics = "request-topic", containerFactory = "testAllKafkaListenerContainerFactory", batch = "true")
    public void listenTestAllKafkaTopic(@Payload List<String> message,
                                        @Header(KafkaHeaders.GROUP_ID) String groupId) throws IOException {
        log.info("\nReceived Message from Kafka\n" +
                "\nSingle headers:\n" +
                "groupId      = " + groupId + "\n" +
                "\nMessage:\n" + message + "\n");

        if (true) {
            throw new RuntimeException("Oops :(");
        }
    }
}
