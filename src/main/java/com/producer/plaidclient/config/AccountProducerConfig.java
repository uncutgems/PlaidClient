package com.producer.plaidclient.config;

import com.producer.plaidclient.entity.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class AccountProducerConfig {
    @Bean
    public KafkaTemplate<String, Account> accountKafkaTemplate() {
        return new KafkaTemplate<>(accountProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Account> accountProducerFactory() {
        Map<String, Object> configs = Environment.kafkaConfigs();
        return new DefaultKafkaProducerFactory<>(configs);
    }
}
