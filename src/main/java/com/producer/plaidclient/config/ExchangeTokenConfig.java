package com.producer.plaidclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class ExchangeTokenConfig {
    @Bean
    public KafkaTemplate<String, String> exchangeTokenKafkaTemplate() {
        return new KafkaTemplate<>(exchangeTokenFactory());
    }

    @Bean
    public ProducerFactory<String, String> exchangeTokenFactory() {
        Map<String, Object> configs = Environment.kafkaConfigs();
        return new DefaultKafkaProducerFactory<>(configs);
    }
}
