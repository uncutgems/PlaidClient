package com.producer.plaidclient.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    public static final String sandboxEnv = "https://sandbox.plaid.com";
    public static final String sandboxClientId = "6398a26c66a3830013af8bc9";
    public static final String sandboxSecret = "022fa09498fdc9057b118c69c19498";
    public static final String localDevelopment = "http://localhost:8080";

    public static Map<String, Object> kafkaConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        return configs;
    }
}
