package com.example.testall.kafka;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//@Service
public class KafkaTestListenerAppJournal {

    private static final Logger log = LoggerFactory.getLogger(KafkaTestListenerAppJournal.class);

    private KafkaTemplate<String, String> applicationKafkaTemplate;

    @KafkaListener(topics = "journal_writer_topic_zone11", containerFactory = "applicationJournalKafkaListenerContainerFactory")
    public void listenTestAllKafkaTopic(@Payload String consumerMessage,
                                        @Headers MessageHeaders messageHeaders) {
        Message<String> message = MessageBuilder
                .withPayload(consumerMessage)
                .setHeader(KafkaHeaders.TOPIC, "big-topic")
                .build();
        applicationKafkaTemplate.send(message);
    }
}
