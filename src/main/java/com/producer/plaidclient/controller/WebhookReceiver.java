package com.producer.plaidclient.controller;

import com.producer.plaidclient.plaid.webhook.PlaidWebhook;
import com.producer.plaidclient.service.WebhookProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plaid-client")
@RequiredArgsConstructor
public class WebhookReceiver {

    private final WebhookProducer webhookProducer;
    @PostMapping("/webhook")
    public ResponseEntity<?> webhookReceiver(@RequestBody PlaidWebhook transactionWebhook) {
        webhookProducer.fetchWebhook(transactionWebhook);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
