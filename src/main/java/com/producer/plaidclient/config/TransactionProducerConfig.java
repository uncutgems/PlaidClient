package com.producer.plaidclient.config;

import com.producer.plaidclient.entity.Transaction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class TransactionProducerConfig {
    @Bean
    public KafkaTemplate<String, Transaction> transactionKafkaTemplate() {
        return new KafkaTemplate<>(transactionProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Transaction> transactionProducerFactory() {
        Map<String, Object> configs = Environment.kafkaConfigs();
        return new DefaultKafkaProducerFactory<>(configs);
    }

}
