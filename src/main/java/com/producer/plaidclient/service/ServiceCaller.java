package com.producer.plaidclient.service;

import com.producer.plaidclient.entity.Item;
import com.producer.plaidclient.plaid.webhook.PlaidWebhook;
import com.producer.plaidclient.repository.ItemRepository;
import com.producer.plaidclient.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ServiceCaller {
    private final TransactionService transactionService;
    private final AccountService accountService;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @KafkaListener(topics = {"webhook"}, groupId = "consumer_1")
    public void serviceWebhookMatcher(PlaidWebhook plaidWebhook) throws URISyntaxException, ParseException {

        if (Objects.equals(plaidWebhook.getWebhookType(), "ITEM")) {
            switch (plaidWebhook.getWebhookCode()) {
                case "ERROR":
                    userRepository.notifyValidityByItemId(plaidWebhook.getItemId(), "Error");
                case "NEW_ACCOUNTS_AVAILABLE":
                    userRepository.notifyValidityByItemId(plaidWebhook.getItemId(), "NewAccount");
                case "PENDING_EXPIRATION":
                    userRepository.notifyValidityByItemId(plaidWebhook.getItemId(), "Expiring");
                case "USER_PERMISSION_REVOKED":
                    Item item = itemRepository.findByItemId(plaidWebhook.getItemId());
                    itemRepository.delete(item);
                case "WEBHOOK_UPDATE_ACKNOWLEDGE":
            }
        } else if (Objects.equals(plaidWebhook.getWebhookType(), "TRANSACTION")) {
            switch (plaidWebhook.getWebhookCode()) {
                case "SYNC_UPDATES_AVAILABLE":
                    // TODO: save directly to database.
                    transactionService.fetchTransaction(plaidWebhook.getItemId());
                    accountService.fetchBalance(plaidWebhook.getItemId());
                case "RECURRING_TRANSACTION_UPDATE":
                case "INITIAL_UPDATE":
                case "HISTORICAL_UPDATE":
                case "DEFAULT_UPDATE":
                case "TRANSACTIONS_REMOVED":
                default:

            }
        }
    }

//    @KafkaListener(topics = {"register"})
//    public void getAccessToken(String publicToken) throws URISyntaxException {
//        ExchangeTokenRequestContract exchangeTokenRequestContract = new ExchangeTokenRequestContract();
//        exchangeTokenRequestContract.setClientId(Environment.sandboxClientId);
//        exchangeTokenRequestContract.setSecret(Environment.sandboxSecret);
//        exchangeTokenRequestContract.setPublicToken(publicToken);
//        RestTemplate restTemplate = new RestTemplate();
//        URI uri = new URI(Environment.sandboxEnv + "/item/public_token/exchange");
//        ResponseEntity<ExchangeTokenResponseContract> response =
//                restTemplate.postForEntity(uri, exchangeTokenRequestContract, ExchangeTokenResponseContract.class);
//        if (response.getBody() != null && response.getStatusCode().is2xxSuccessful()) {
//            ExchangeTokenResponseContract exchangeTokenResponseContract = response.getBody();
//            Item item = new Item();
//            item.setItemId(exchangeTokenResponseContract.getItemId());
//            item.setAccessToken(exchangeTokenResponseContract.getAccessToken());
//            itemRepository.save(item);
//        }
//    }
}
