package com.example.testall.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "test-all.kafka")
@Validated
public class KafkaConfigurationProperties {

    /**
     * Рубильник Kafka для CorpAPI
     */
    private boolean enabled;

    /**
     * Сервера Kafka CorpAPI
     */
    @NotEmpty
    private List<String> bootstrapServers;

    /**
     * Настройки producer для Kafka CorpAPI
     */
    private Map<String, Object> producerConfig;

    /**
     * Настройки consumer для Kafka CorpAPI
     */
    private Map<String, Object> consumerConfig;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public Map<String, Object> getProducerConfig() {
        return producerConfig;
    }

    public void setProducerConfig(Map<String, Object> producerConfig) {
        this.producerConfig = producerConfig;
    }

    public Map<String, Object> getConsumerConfig() {
        return consumerConfig;
    }

    public void setConsumerConfig(Map<String, Object> consumerConfig) {
        this.consumerConfig = consumerConfig;
    }
}
