package com.example.testall.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaLoggingCallback<T> implements ListenableFutureCallback<SendResult<String, T>> {

    private final Logger log = LoggerFactory.getLogger(KafkaLoggingCallback.class);

    private final T message;

    public KafkaLoggingCallback(T message) {
        this.message = message;
    }

    @Override
    public void onFailure(@NonNull Throwable ex) {
        String errorMessage = String.format("Невозможно доставить сообщение с %s", message);
        log.error(errorMessage, ex);
    }

    @Override
    public void onSuccess(SendResult<String, T> result) {
        if (result != null) {
            log.info("Сообщение c {} отправлено, offset = {}", message, result.getRecordMetadata().offset());
        } else {
            log.info("Сообщение c {} отправлено, result is null", message);
        }
    }
}
