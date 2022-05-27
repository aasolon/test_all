package com.example.testall.kafka;

public class KafkaListenerCustomException extends RuntimeException {

    public KafkaListenerCustomException(String message) {
        super(message);
    }
}
