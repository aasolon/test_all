package com.example.testall.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.ListenerExecutionFailedException;

public class KafkaConsumerRecordRecoverer implements ConsumerRecordRecoverer {

    private static final Logger log = LoggerFactory.getLogger(KafkaTestListener.class);


    @Override
    public void accept(ConsumerRecord<?, ?> consumerRecord, Exception e) {
        if (e instanceof ListenerExecutionFailedException &&
                e.getCause() != null && e.getCause() instanceof KafkaListenerCustomException) {
            log.error("================ KafkaListenerCustomException on record " + consumerRecord.value());
        } else {
            log.error("================ Unknown exception on record "  + consumerRecord.value());
        }
    }
}
