package com.producer.plaidclient.config;

import com.producer.plaidclient.plaid.webhook.PlaidWebhook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
public class WebhookProducerConfig {
    @Bean
    public KafkaTemplate<String, PlaidWebhook> webhookKafkaTemplate() {
        return new KafkaTemplate<>(webhookProducerFactory());
    }

    @Bean
    public ProducerFactory<String, PlaidWebhook> webhookProducerFactory() {
        Map<String, Object> configs = Environment.kafkaConfigs();
        return new DefaultKafkaProducerFactory<>(configs);
    }
}
