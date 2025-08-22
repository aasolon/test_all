package com.example.testall.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

public class KafkaLoggingBiConsumer<T> implements BiConsumer<SendResult<String, T>, Throwable> {

    private final Logger log = LoggerFactory.getLogger(KafkaLoggingBiConsumer.class);

    private final T message;

    public KafkaLoggingBiConsumer(T message) {
        this.message = message;
    }

    @Override
    public void accept(SendResult<String, T> result, Throwable ex) {
        if (ex != null) {
            if (result != null) {
                log.info("Сообщение c {} отправлено, offset = {}", message, result.getRecordMetadata().offset());
            } else {
                log.info("Сообщение c {} отправлено, result is null", message);
            }
        } else {
            String errorMessage = String.format("Невозможно доставить сообщение с %s", message);
            log.error(errorMessage, ex);
        }
    }
}
