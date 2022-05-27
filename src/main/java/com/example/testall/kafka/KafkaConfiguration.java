package com.example.testall.kafka;

import com.example.testall.simplecontroller.SimpleController;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.MessagingMessageConverter;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class KafkaConfiguration {

    private static final Logger log = LoggerFactory.getLogger(KafkaConfiguration.class);

    @Bean
    public KafkaTemplate<String, String> testAllKafkaTemplate(KafkaConfigurationProperties properties) {
        Map<String, Object> configProps = new HashMap<>();
        if (properties.getProducerConfig() != null) {
            configProps.putAll(properties.getProducerConfig());
        }
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, Integer.MAX_VALUE);
        configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1048576000);
        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(configProps);
        KafkaTemplate<String, String> stringStringKafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);
//        stringStringKafkaTemplate.setProducerListener(new ProducerListener<>() {
//            @Override
//            public void onError(ProducerRecord<String, String> producerRecord, RecordMetadata recordMetadata, Exception exception) {
//                log.error("Error from ProducerListener", exception);
//                int i = 0;
//            }
//        });
        return stringStringKafkaTemplate;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> testAllKafkaListenerContainerFactory(KafkaConfigurationProperties properties) {
        Map<String, Object> configProps = new HashMap<>();
        if (properties.getConsumerConfig() != null) {
            configProps.putAll(properties.getConsumerConfig());
        }
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getBootstrapServers());
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        DefaultKafkaConsumerFactory<Object, Object> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(configProps);

        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
//        MessagingMessageConverter messageConverter = new MessagingMessageConverter();
//        DefaultKafkaHeaderMapper headerMapper = new DefaultKafkaHeaderMapper();
//        headerMapper.addTrustedPackages("*");
//        messageConverter.setHeaderMapper(headerMapper);
//        kafkaListenerContainerFactory.setMessageConverter(messageConverter);

        kafkaListenerContainerFactory.setConsumerFactory(kafkaConsumerFactory);
        kafkaListenerContainerFactory.setCommonErrorHandler(new DefaultErrorHandler(
                new KafkaConsumerRecordRecoverer(),
                new FixedBackOff(0, 0)
        ));
        return kafkaListenerContainerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> applicationJournalKafkaListenerContainerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "appjournal");
        DefaultKafkaConsumerFactory<Object, Object> kafkaConsumerFactory = new DefaultKafkaConsumerFactory<>(configProps);
        ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        kafkaListenerContainerFactory.setConsumerFactory(kafkaConsumerFactory);
        kafkaListenerContainerFactory.setConcurrency(1);
//        kafkaListenerContainerFactory.setRecordFilterStrategy(1);
        return kafkaListenerContainerFactory;
    }

    @Bean
    public KafkaTemplate<String, String> applicationKafkaTemplate() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        DefaultKafkaProducerFactory<String, String> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(configProps);
        return new KafkaTemplate<>(kafkaProducerFactory);
    }
}
