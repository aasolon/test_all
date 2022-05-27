package com.example.testall.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.SerializationUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@RestController
public class KafkaController {

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaTemplate<String, String> testAllKafkaTemplate;

    @GetMapping("/test-kafka")
    public void kafkaTest() throws IOException, InterruptedException {
        String text = "hello_" + new Random().nextInt(999);
//        String text = "";
        KafkaObjectHeader objectHeader = new KafkaObjectHeader();
        objectHeader.setCount(11);
        objectHeader.setName("some_name");
        objectHeader.setName2("some_name_2");
        Message<String> message = MessageBuilder
//                .withPayload(text)
//                .withPayload(text + IOUtils.toString(new byte[48576]))
                .withPayload(text + IOUtils.toString(new byte[104857600])) // 50 mb
//                .withPayload(IOUtils.toString(new FileSystemResource("VID_20190524_215856.mp4").getInputStream()))
                .setHeader(KafkaHeaders.TOPIC, "big-topic")
                .setHeader("stringHeader", "corpApi")
                .setHeader("longHeader", 11)
                .setHeader("uuidHeader", UUID.randomUUID())
                .setHeader("byteHeader", new byte[10])
                .setHeader("objectArrayHeader", new ObjectMapper().writeValueAsBytes(Arrays.asList(objectHeader, objectHeader)))
//                .setHeader("objectHeader", objectHeader)
                .setHeader("objectHeader", new ObjectMapper().writeValueAsBytes(objectHeader))
//                .setHeader("objectHeader", SerializationUtils.serialize(objectHeader))
                .build();
        ListenableFuture<SendResult<String, String>> future = testAllKafkaTemplate.send(message);
        future.addCallback(new KafkaLoggingCallback<>(text));
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                int i = 0;
                ListenableFuture<SendResult<String, String>> send = testAllKafkaTemplate.send(MessageBuilder.createMessage("", message.getHeaders()));
                send.addCallback(new KafkaLoggingCallback<>(text));
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                int i = 0;
            }
        });
    }
}
