package com.producer.plaidclient.service;

import com.producer.plaidclient.plaid.webhook.PlaidWebhook;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebhookProducer {
    private static final String WEBHOOK_TOPIC = "webhook";
    private final KafkaTemplate<String, PlaidWebhook> kafkaTemplate;

    public void fetchWebhook(PlaidWebhook transactionWebhook) {
        kafkaTemplate.send(WEBHOOK_TOPIC, transactionWebhook);
    }

}
